package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.controller.response.RegisterResponse;
import com.earth_chat.auth.mapper.AuthMapper;
import com.earth_chat.auth.service.AuthService;
import com.earth_chat.common.enums.UserStatus;
import com.earth_chat.common.exception.AlreadyExistsEmailException;
import com.earth_chat.common.exception.AlreadyExistsNicknameException;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (authMapper.existsEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsEmailException("이미 가입된 이메일입니다.");
        }

        if (authMapper.existsNickname(registerRequest.getNickname())) {
            throw new AlreadyExistsNicknameException("이미 가입된 닉네임입니다.");
        }

        UserVo userVo = UserVo.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .pwd(passwordEncoder.encode(registerRequest.getPassword()))
                .translateCode(registerRequest.getTranslateCode())
                .userStatus(UserStatus.EMAIL_AUTH)
                .build();

        authMapper.insertUser(userVo);

        return RegisterResponse.builder()
                .userSeq(userVo.getUserSeq())
                .nickname(userVo.getNickname())
                .email(userVo.getEmail())
                .userStatus(userVo.getUserStatus())
                .build();
    }
}
