package com.earth_chat.common.jwt;

import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }

        if(token != null && jwtTokenProvider.validateToken(token)){

            Claims claims = jwtTokenProvider.parseToken(token);

            List<SimpleGrantedAuthority> auth = jwtTokenProvider.getAuthorities(claims);

            UserVo user = userService.selectUserByEmail(claims.getSubject())
                    .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 사용자입니다."));
            List<RoleVo> roleList = userService.selectRolesByUserSeq(user.getUserSeq());
            user.setRoleList(roleList);

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
