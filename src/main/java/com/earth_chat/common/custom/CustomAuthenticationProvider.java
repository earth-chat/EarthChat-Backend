package com.earth_chat.common.custom;

import com.earth_chat.common.enums.MessageCode;
import com.earth_chat.common.exception.PasswordNotMatchesException;
import com.earth_chat.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final MessageUtil messageUtil;

    /**
     * 인증 처리.
     * @param authentication 인증되지 않은 Authentication 객체
     * @return 인증된 Authentication 객체
     * @throws AuthenticationException 인증 실패 시 발생
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode()));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new PasswordNotMatchesException(messageUtil.getMessage(MessageCode.USER_INVALID_PASSWORD.getCode()));
        }

        return new UsernamePasswordAuthenticationToken(userDetails, authentication, userDetails.getAuthorities());
    }

    /**
     * 대응 타입 구분.
     * @param authentication Class
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
