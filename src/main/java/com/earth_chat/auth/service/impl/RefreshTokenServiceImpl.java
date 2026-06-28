package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.mapper.RefreshTokenMapper;
import com.earth_chat.auth.service.RefreshTokenService;
import com.earth_chat.auth.vo.RefreshTokenVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.util.MessageUtil;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;
    private final UserService userService;
    private final MessageUtil messageUtil;

    @Value("${refresh-token.expired.time}")
    private long refreshTokenExpiredTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public RefreshTokenVo createToken(CustomUserDetails customUserDetails) {

        UserVo userVo = userService.selectUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("user.not-found")));

        LocalDateTime now = LocalDateTime.now();

        RefreshTokenVo refreshToken = RefreshTokenVo.builder()
                .userSeq(userVo.getUserSeq())
                .tokenValue(UUID.randomUUID().toString())
                .expiredDt(now.plusSeconds(refreshTokenExpiredTime / 1000))
                .useYn("Y")
                .build();

        refreshTokenMapper.insertRefreshToken(refreshToken);

        return refreshToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RefreshTokenVo> selectRefreshTokenByUserSeq(Long userSeq) {
        return Optional.ofNullable(refreshTokenMapper.selectRefreshTokenByUserSeq(userSeq));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RefreshTokenVo> selectRefreshTokenByTokenValue(String refreshToken) {
        return Optional.ofNullable(refreshTokenMapper.selectRefreshTokenByTokenValue(refreshToken));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateRefreshToken(RefreshTokenVo refreshTokenVo) {
        return refreshTokenMapper.updateRefreshToken(refreshTokenVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteRefreshTokenByTokenSeq(Long tokenSeq) {
        return refreshTokenMapper.deleteRefreshTokenByTokenSeq(tokenSeq);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteRefreshTokenByUserSeq(Long userSeq) {
        return refreshTokenMapper.deleteRefreshTokenByUserSeq(userSeq);
    }
}
