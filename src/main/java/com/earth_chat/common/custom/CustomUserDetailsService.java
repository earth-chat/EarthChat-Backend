package com.earth_chat.common.custom;

import com.earth_chat.common.util.MessageUtil;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final MessageUtil messageUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userService.selectUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("user.not-found")));

        List<RoleVo> roleList = userService.selectRolesByUserSeq(userVo.getUserSeq());
        userVo.setRoleList(roleList);

        return new CustomUserDetails(userVo);
    }
}
