package com.earth_chat.auth.service;

import com.earth_chat.auth.vo.EmailAuthInfoVo;
import com.earth_chat.auth.vo.PasswordFindKeyVo;

public interface EmailService {

    /**
     * 회원가입 인증 코드 메일 발송
     * @param emailAuthInfoVo 인증 코드 정보
     */
    void sendAuthCodeMail(EmailAuthInfoVo emailAuthInfoVo);

    /**
     * 비밀번호 찾기 메일 발송
     * @param passwordFindKeyVo 비밀번호 찾기 키
     */
    void sendPasswordFindKeyMail(PasswordFindKeyVo passwordFindKeyVo);
}
