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
}
