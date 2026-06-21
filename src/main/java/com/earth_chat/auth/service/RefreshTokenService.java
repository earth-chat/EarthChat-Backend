package com.earth_chat.auth.service;

import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;

import java.util.Optional;

public interface RefreshTokenService {

    /**
     * Refresh Token 생성.
     * @param customUserDetails 인증된 사용자 객체
     * @return RefreshTokenVo
     */
    RefreshTokenVo createToken(CustomUserDetails customUserDetails);

    /**
     * Refresh Token 조회.
     * @param userSeq 사용자 SEQ
     * @return RefreshTokenVo
     */
    Optional<RefreshTokenVo> selectRefreshTokenByUserSeq(Long userSeq);

    /**
     * Refresh Token 조회.
     * @param refreshToken Refresh Token
     * @return RefreshTokenVo
     */
    Optional<RefreshTokenVo> selectRefreshTokenByTokenValue(String refreshToken);

    /**
     * Refresh Token 정보 업데이트.
     * @param refreshTokenVo Refresh Token
     * @return int
     */
    int updateRefreshToken(RefreshTokenVo refreshTokenVo);

    /**
     * Refresh Token 정보 제거.
     * @param tokenSeq 토큰 SEQ
     * @return int
     */
    int deleteRefreshTokenByTokenSeq(Long tokenSeq);

    /**
     * Refresh Token 정보 제거.
     * @param userSeq 사용자 SEQ
     * @return int
     */
    int deleteRefreshTokenByUserSeq(Long userSeq);
}
