package com.earth_chat.auth.service;

import com.earth_chat.auth.controller.request.SendMailRequest;
import com.earth_chat.auth.vo.EmailAuthInfoVo;

public interface EmailService {

    /**
     * 회원가입 인증 코드 메일 발송
     * @param emailAuthInfoVo 메일 발송 요청 객체
     */
    void sendAuthCodeMail(EmailAuthInfoVo emailAuthInfoVo);
}
