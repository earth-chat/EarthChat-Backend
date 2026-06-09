package com.earth_chat.auth.service;

import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.controller.response.RegisterResponse;

public interface AuthService {

    /**
     * 회원가입 처리 메서드.
     * @param registerRequest 회원가입 요청 객체
     * @return RegisterResponse
     */
    public RegisterResponse register(RegisterRequest registerRequest);
}
