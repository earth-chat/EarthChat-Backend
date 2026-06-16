package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.controller.request.LoginRequest;
import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.controller.request.SendMailRequest;
import com.earth_chat.auth.controller.request.ValidateMailAuthCodeRequest;
import com.earth_chat.auth.controller.response.LoginResponse;
import com.earth_chat.auth.controller.response.RegisterResponse;
import com.earth_chat.auth.service.AuthService;
import com.earth_chat.auth.service.EmailAuthInfoService;
import com.earth_chat.auth.service.EmailService;
import com.earth_chat.auth.service.RefreshTokenService;
import com.earth_chat.auth.vo.EmailAuthInfoVo;
import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.MailType;
import com.earth_chat.common.enums.UserStatus;
import com.earth_chat.common.exception.*;
import com.earth_chat.common.jwt.JwtTokenProvider;
import com.earth_chat.common.util.AuthNumUtil;
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (userService.existsEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsEmailException("이미 가입된 이메일입니다.");
        }

        if (userService.existsNickname(registerRequest.getNickname())) {
            throw new AlreadyExistsNicknameException("이미 가입된 닉네임입니다.");
        }

        EmailAuthInfoVo emailAuthInfo = emailAuthInfoService.selectEmailAuthInfoByEmail(registerRequest.getEmail())
                .orElseThrow(() -> new EmailAuthInfoNotFoundException("이메일 인증 정보를 찾을 수 없습니다."));

        if (!emailAuthInfo.getAuthYn().equals("Y")) {
            throw new RequiredEmailAuthException("이메일 인증이 필요합니다.");
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
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        UserVo userVo = userService.selectUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 사용자입니다."));

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
        LocalDateTime expiredDt = now.plusMinutes(3);

        EmailAuthInfoVo emailAuthInfoVo = EmailAuthInfoVo.builder()
                .authNum(AuthNumUtil.generateAuthNum(6))
                .email(request.getEmail())
                .useYn("Y")
                .authYn("N")
                .expiredDt(expiredDt)
                .regDt(now)
                .build();

        emailAuthInfoService.selectEmailAuthInfoByEmail(emailAuthInfoVo.getEmail())
                .ifPresent(authInfo -> {
                    authInfo.setUseYn("N");
                    emailAuthInfoService.updateEmailAuthInfo(authInfo);
                });

        emailAuthInfoService.insertEmailAuthInfo(emailAuthInfoVo);
        emailService.sendAuthCodeMail(emailAuthInfoVo);
    }

    @Override
    public Map<String, Object> validateMailAuthCode(ValidateMailAuthCodeRequest request) {
        MailType type = request.getType();
        String email = request.getEmail();
        String authNum = request.getAuthNum();
        Map<String, Object> result = new HashMap<>();

        switch (type) {
            case REGISTER -> {
                EmailAuthInfoVo authInfo = emailAuthInfoService.selectEmailAuthInfoByEmail(email)
                        .orElseThrow(() -> new InvalidEmailAuthNumException("잘못된 인증코드입니다."));

                if (authInfo.getUseYn().equals("N")) {
                    throw new InvalidEmailAuthNumException("사용할 수 없는 인증 코드입니다.");
                }

                if (authInfo.getExpiredDt().isBefore(LocalDateTime.now())) {
                    authInfo.setUseYn("N");
                    emailAuthInfoService.updateEmailAuthInfo(authInfo);
                    throw new InvalidEmailAuthNumException("만료된 인증 코드입니다.");
                }

                if (!authInfo.getAuthNum().equals(authNum)) {
                    throw new InvalidEmailAuthNumException("인증 코드가 일치하지 않습니다.");
                }

                authInfo.setUseYn("N");
                authInfo.setAuthYn("Y");
                emailAuthInfoService.updateEmailAuthInfo(authInfo);

                result.put("isValid", true);
            }

            case PASSWORD -> {
                // TODO: 구현 예정
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
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        refreshTokenService.deleteRefreshTokenByUserSeq(user.getUserSeq());
    }

}
