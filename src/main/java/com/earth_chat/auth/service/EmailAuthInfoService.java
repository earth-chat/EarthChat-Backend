package com.earth_chat.auth.service;

import com.earth_chat.auth.vo.EmailAuthInfoVo;

import java.util.Optional;

public interface EmailAuthInfoService {

    /**
     * 이메일 인증 정보 삽입.
     * @param emailAuthInfoVo 이메일 인증 정보
     * @return int
     */
    int insertEmailAuthInfo(EmailAuthInfoVo emailAuthInfoVo);

    /**
     * 이메일 인증 정보 조회.
     * @param email 이메일
     * @return EmailAuthInfoVo
     */
    Optional<EmailAuthInfoVo> selectEmailAuthInfoByEmail(String email);

    /**
     * 이메일 인증 정보 업데이트.
     * @param authInfo 인증 정보
     */
    int updateEmailAuthInfo(EmailAuthInfoVo authInfo);

    /**
     * 인증이 완료된 정보가 존재하는지 확인.
     * @param email 이메일
     * @return boolean
     */
    boolean existsAuthedInfoByEmail(String email);
}
