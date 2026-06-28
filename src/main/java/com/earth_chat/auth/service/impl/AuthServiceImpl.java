package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.controller.request.*;
import com.earth_chat.auth.controller.response.LoginResponse;
import com.earth_chat.auth.controller.response.RegisterResponse;
import com.earth_chat.auth.service.*;
import com.earth_chat.auth.vo.EmailAuthInfoVo;
import com.earth_chat.auth.vo.PasswordFindKeyVo;
import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.MailType;
import com.earth_chat.common.enums.MessageCode;
import com.earth_chat.common.enums.UserStatus;
import com.earth_chat.common.exception.*;
import com.earth_chat.common.jwt.JwtTokenProvider;
import com.earth_chat.common.util.AuthNumUtil;
import com.earth_chat.common.util.MessageUtil;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final EmailAuthInfoService emailAuthInfoService;
    private final PasswordFindKeyService passwordFindKeyService;
    private final MessageUtil messageUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (userService.existsEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsEmailException(messageUtil.getMessage(MessageCode.USER_DUPLICATE_EMAIL.getCode()));
        }

        if (userService.existsNickname(registerRequest.getNickname())) {
            throw new AlreadyExistsNicknameException(messageUtil.getMessage(MessageCode.USER_DUPLICATE_NICKNAME.getCode()));
        }

        if (emailAuthInfoService.existsAuthedInfoByEmail(registerRequest.getEmail())) {
            throw new EmailAuthInfoNotFoundException(messageUtil.getMessage(MessageCode.USER_REQUIRED_EMAIL_AUTH.getCode()));
        }

        List<RoleVo> roleList = userService.selectUserRoles();

        UserVo userVo = UserVo.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .pwd(passwordEncoder.encode(registerRequest.getPassword()))
                .translateCode(registerRequest.getTranslateCode())
                .userStatus(UserStatus.COMPLETED)
                .roleList(roleList)
                .build();

        userService.insertUser(userVo);
        userService.insertUserRole(userVo);

        return RegisterResponse.builder()
                .userSeq(userVo.getUserSeq())
                .nickname(userVo.getNickname())
                .email(userVo.getEmail())
                .userStatus(userVo.getUserStatus())
                .translateCode(userVo.getTranslateCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        UserVo userVo = userService.selectUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createToken(principal);
        RefreshTokenVo refreshTokenVo = refreshTokenService.createToken(principal);

        return LoginResponse.builder()
                .userSeq(userVo.getUserSeq())
                .nickname(userVo.getNickname())
                .translateCode(userVo.getTranslateCode())
                .accessToken(accessToken)
                .refreshToken(refreshTokenVo.getTokenValue())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> existsEmail(String email) {
        boolean isExists = userService.existsEmail(email);

        Map<String, Object> result = new HashMap<>();
        result.put("isExists", isExists);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> existsNickname(String nickname) {
        boolean isExists = userService.existsNickname(nickname);

        Map<String, Object> result = new HashMap<>();
        result.put("isExists", isExists);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void sendMail(SendMailRequest request) {
        LocalDateTime now = LocalDateTime.now();

        switch (request.getType()) {
            case REGISTER -> {
                LocalDateTime expiredDt = now.plusMinutes(3);

                EmailAuthInfoVo emailAuthInfoVo = EmailAuthInfoVo.builder()
                        .authNum(AuthNumUtil.generateAuthNum(6))
                        .email(request.getEmail())
                        .useYn("Y")
                        .authYn("N")
                        .expiredDt(expiredDt)
                        .build();

                emailAuthInfoService.selectEmailAuthInfoByEmail(emailAuthInfoVo.getEmail())
                        .ifPresent(authInfo -> {
                            authInfo.setUseYn("N");
                            emailAuthInfoService.updateEmailAuthInfo(authInfo);
                        });

                emailAuthInfoService.insertEmailAuthInfo(emailAuthInfoVo);
                emailService.sendAuthCodeMail(emailAuthInfoVo);
            }

            case PASSWORD -> {
                userService.selectUserByEmail(request.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

                LocalDateTime expiredDt = now.plusMinutes(10);

                PasswordFindKeyVo passwordFindKeyVo = PasswordFindKeyVo.builder()
                        .email(request.getEmail())
                        .keyValue(UUID.randomUUID().toString())
                        .useYn("Y")
                        .expiredDt(expiredDt)
                        .build();

                passwordFindKeyService.selectPasswordFindKeyByEmail(passwordFindKeyVo.getEmail())
                                .ifPresent(passwordFindKey -> {
                                    passwordFindKey.setUseYn("N");
                                    passwordFindKeyService.updatePasswordFindKey(passwordFindKey);
                                });

                passwordFindKeyService.insertPasswordFindKey(passwordFindKeyVo);
                emailService.sendPasswordFindKeyMail(passwordFindKeyVo);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> validateMailAuthCode(ValidateMailAuthCodeRequest request) {
        MailType type = request.getType();
        String email = request.getEmail();
        String authNum = request.getAuthNum();
        Map<String, Object> result = new HashMap<>();

        switch (type) {
            case REGISTER -> {

                EmailAuthInfoVo authInfo = emailAuthInfoService.selectEmailAuthInfoByEmail(email)
                        .orElseThrow(() -> new InvalidEmailAuthNumException(messageUtil.getMessage(MessageCode.AUTH_CODE_INVALID.getCode())));

                if (authInfo.getExpiredDt().isBefore(LocalDateTime.now())) {
                    authInfo.setUseYn("N");
                    emailAuthInfoService.updateEmailAuthInfo(authInfo);
                    throw new InvalidEmailAuthNumException(messageUtil.getMessage(MessageCode.AUTH_CODE_EXPIRED.getCode()));
                }

                if (!authInfo.getAuthNum().equals(authNum)) {
                    throw new InvalidEmailAuthNumException(messageUtil.getMessage(MessageCode.AUTH_CODE_INVALID.getCode()));
                }

                authInfo.setUseYn("N");
                emailAuthInfoService.updateEmailAuthInfo(authInfo);

                result.put("isValid", true);
            }

            case PASSWORD -> {
                userService.selectUserByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

                PasswordFindKeyVo passwordFindKeyVo = passwordFindKeyService.selectPasswordFindKeyByEmail(email)
                        .orElseThrow(() -> new InvalidPasswordFindKeyException(messageUtil.getMessage(MessageCode.PASSWORD_KEY_INVALID.getCode())));

                if (passwordFindKeyVo.getExpiredDt().isBefore(LocalDateTime.now())) {
                    passwordFindKeyVo.setUseYn("N");
                    passwordFindKeyService.updatePasswordFindKey(passwordFindKeyVo);
                    throw new InvalidPasswordFindKeyException(messageUtil.getMessage(MessageCode.PASSWORD_KEY_EXPIRED.getCode()));
                }

                if (!passwordFindKeyVo.getKeyValue().equals(authNum)) {
                    throw new InvalidPasswordFindKeyException(messageUtil.getMessage(MessageCode.PASSWORD_KEY_INVALID.getCode()));
                }

                result.put("isValid", true);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        UserVo user = userService.selectUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        refreshTokenService.deleteRefreshTokenByUserSeq(user.getUserSeq());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPassword(PasswordResetRequest request) {
        PasswordFindKeyVo passwordFindKey = passwordFindKeyService.selectPasswordFindKeyByKey(request.getKey())
                .orElseThrow(() -> new PasswordFindKeyNotFoundException(messageUtil.getMessage(MessageCode.PASSWORD_KEY_INVALID.getCode())));

        if (passwordFindKey.getExpiredDt().isBefore(LocalDateTime.now())) {
            passwordFindKey.setUseYn("N");
            passwordFindKeyService.updatePasswordFindKey(passwordFindKey);
            throw new InvalidPasswordFindKeyException(messageUtil.getMessage(MessageCode.PASSWORD_KEY_EXPIRED.getCode()));
        }

        UserVo user = userService.selectUserByEmail(passwordFindKey.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        user.setPwd(passwordEncoder.encode(request.getPassword()));
        userService.updateUserPassword(user);

        passwordFindKey.setUseYn("N");
        passwordFindKeyService.updatePasswordFindKey(passwordFindKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoginResponse refresh(TokenRefreshRequest request) {
        String refreshToken = request.getToken();

        RefreshTokenVo refreshTokenVo = refreshTokenService.selectRefreshTokenByTokenValue(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException(messageUtil.getMessage(MessageCode.REFRESH_TOKEN_NOT_FOUND.getCode())));

        if (refreshTokenVo.getExpiredDt().isBefore(LocalDateTime.now())) {
            refreshTokenVo.setUseYn("N");
            refreshTokenService.updateRefreshToken(refreshTokenVo);
            throw new InvalidRefreshTokenException(messageUtil.getMessage(MessageCode.REFRESH_TOKEN_EXPIRED.getCode()));
        }

        UserVo user = userService.selectUserByUserSeq(refreshTokenVo.getUserSeq())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        List<RoleVo> roleList = userService.selectRolesByUserSeq(user.getUserSeq());
        user.setRoleList(roleList);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        String accessToken = jwtTokenProvider.createToken(customUserDetails);

        return LoginResponse.builder()
                .userSeq(user.getUserSeq())
                .nickname(user.getNickname())
                .translateCode(user.getTranslateCode())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
