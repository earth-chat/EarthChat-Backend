package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.mapper.RefreshTokenMapper;
import com.earth_chat.auth.service.RefreshTokenService;
import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;
    private final UserService userService;

    @Value("${refresh-token.expired.time}")
    private long refreshTokenExpiredTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public RefreshTokenVo createToken(CustomUserDetails customUserDetails) {

        UserVo userVo = userService.selectUserByEmail(customUserDetails.getUsername());
        if (userVo == null) {
            throw new UsernameNotFoundException("가입되지 않은 사용자입니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        RefreshTokenVo refreshToken = RefreshTokenVo.builder()
                .userSeq(userVo.getUserSeq())
                .tokenValue(UUID.randomUUID().toString())
                .expiredDt(now.plusSeconds(refreshTokenExpiredTime / 1000))
                .useYn("Y")
                .regDt(now)
                .modDt(now)
                .build();

        refreshTokenMapper.insertRefreshToken(refreshToken);

        return refreshToken;
    }
}
