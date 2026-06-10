package com.earth_chat.common.custom;

import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserVo userVo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userVo.getRoleList().stream()
                .map(RoleVo::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return userVo.getPwd();
    }

    @Override
    public String getUsername() {
        return userVo.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return userVo.getUseYn().equals("Y");
    }
}
