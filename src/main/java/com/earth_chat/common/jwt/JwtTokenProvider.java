package com.earth_chat.common.jwt;

import com.earth_chat.common.custom.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private final SecretKey SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRED_MS;

    public JwtTokenProvider(
            @Value("${token.secret-key}") String secretKey,
            @Value("${access-token.expired.time}") long accessTokenExpiredTime
    ) {
        this.SECRET_KEY= Keys.hmacShaKeyFor(secretKey.getBytes());
        this.ACCESS_TOKEN_EXPIRED_MS = accessTokenExpiredTime;
    }

    /**
     * JWT 토큰 생성.
     * @param customUserDetails 인증된 사용자 객체
     * @return Access Token
     */
    public String createToken(CustomUserDetails customUserDetails) {
        String authorities = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .signWith(SECRET_KEY)
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_MS))
                .compact();
    }

    /**
     * JWT 토큰 파싱.
     * @param token Access Token
     * @return 파싱 결과
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * JWT 토큰 검증.
     * @param token Access Token
     * @return 검증 결과
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            log.error("JwtException 발생 : ", e);

            return false;
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException 발생 : ", e);

            return false;
        } catch (Exception e) {
            log.error("알 수 없는 예외 발생 : ", e);

            return false;
        }
    }

    /**
     * Claims 내에 들어있는 권한 목록 추출.
     * @param claims Access Token
     * @return 권한 목록
     */
    public List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String authorities = claims.get("authorities", String.class);

        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
