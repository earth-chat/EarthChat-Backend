package com.earth_chat.auth.service;

import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;

public interface RefreshTokenService {

    /**
     * Refresh Token 생성.
     * @param customUserDetails 인증된 사용자 객체
     * @return RefreshTokenVo
     */
    RefreshTokenVo createToken(CustomUserDetails customUserDetails);
}
