package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.controller.request.LoginRequest;
import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.controller.request.SendMailRequest;
import com.earth_chat.auth.controller.response.LoginResponse;
import com.earth_chat.auth.controller.response.RegisterResponse;
import com.earth_chat.auth.service.AuthService;
import com.earth_chat.auth.service.RefreshTokenService;
import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.UserStatus;
import com.earth_chat.common.exception.AlreadyExistsEmailException;
import com.earth_chat.common.exception.AlreadyExistsNicknameException;
import com.earth_chat.common.jwt.JwtTokenProvider;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (userService.existsEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsEmailException("이미 가입된 이메일입니다.");
        }

        if (userService.existsNickname(registerRequest.getNickname())) {
            throw new AlreadyExistsNicknameException("이미 가입된 닉네임입니다.");
        }

        List<RoleVo> roleList = userService.selectUserRoles();

        UserVo userVo = UserVo.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .pwd(passwordEncoder.encode(registerRequest.getPassword()))
                .translateCode(registerRequest.getTranslateCode())
                .userStatus(UserStatus.EMAIL_AUTH)
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
    public LoginResponse login(LoginRequest loginRequest) {
        UserVo userVo = userService.selectUserByEmail(loginRequest.getEmail());
        if (userVo == null) {
            throw new UsernameNotFoundException("가입되지 않은 사용자입니다.");
        }

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
}
