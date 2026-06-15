package com.earth_chat.auth.service;

import com.earth_chat.auth.controller.request.LoginRequest;
import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.controller.request.SendMailRequest;
import com.earth_chat.auth.controller.request.ValidateMailAuthCodeRequest;
import com.earth_chat.auth.controller.response.LoginResponse;
import com.earth_chat.auth.controller.response.RegisterResponse;
import com.earth_chat.common.custom.CustomUserDetails;

import java.util.Map;

public interface AuthService {

    /**
     * 회원가입 처리 메서드.
     * @param registerRequest 회원가입 요청 객체
     * @return RegisterResponse
     */
    RegisterResponse register(RegisterRequest registerRequest);

    /**
     * 로그인 처리 메서드.
     * @param loginRequest 로그인 요청 객체
     * @return LoginResponse
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 가입된 이메일이 있는지 확인.
     * @param email 이메일
     * @return boolean
     */
    Map<String, Object> existsEmail(String email);

    /**
     * 가입된 닉네임이 있는지 확인.
     * @param nickname 닉네임
     * @return boolean
     */
    Map<String, Object> existsNickname(String nickname);

    /**
     * 메일 발송.
     * @param request 메일 발송 요청 객체
     */
    void sendMail(SendMailRequest request);

    /**
     * 메일 인증코드 검증.
     * @param request 인증코드 검증 요청 객체
     */
    Map<String, Object> validateMailAuthCode(ValidateMailAuthCodeRequest request);

    /**
     * 로그아웃 처리.
     * @param customUserDetails 인증된 사용자 객체
     */
    void logout(CustomUserDetails customUserDetails);
}
