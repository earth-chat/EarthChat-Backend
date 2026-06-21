package com.earth_chat.auth.mapper;

import com.earth_chat.auth.vo.RefreshTokenVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    /**
     * Refresh Token 데이터 저장.
     * @param refreshTokenVo Refresh Token 데이터
     * @return int
     */
    int insertRefreshToken(RefreshTokenVo refreshTokenVo);

    /**
     * Refresh Token 조회.
     * @param userSeq 사용자 SEQ
     * @return RefreshTokenVo
     */
    RefreshTokenVo selectRefreshTokenByUserSeq(Long userSeq);

    /**
     * Refresh Token 조회.
     * @param refreshToken Refresh Token
     * @return RefreshTokenVo
     */
    RefreshTokenVo selectRefreshTokenByTokenValue(String refreshToken);

    /**
     * Refresh Token 정보 업데이트
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
     * Refresh Token 정보 제거
     * @param userSeq 사용자 SEQ
     * @return int
     */
    int deleteRefreshTokenByUserSeq(Long userSeq);
}
