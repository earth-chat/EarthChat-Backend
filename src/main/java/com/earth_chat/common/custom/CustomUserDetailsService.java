package com.earth_chat.common.custom;

import com.earth_chat.common.enums.MessageCode;
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

    /**
     * 사용자 정보 조회.
     * @param username 사용자 ID
     * @return UserDetails
     * @throws UsernameNotFoundException 사용자를 찾지 못했을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userService.selectUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        List<RoleVo> roleList = userService.selectRolesByUserSeq(userVo.getUserSeq());
        userVo.setRoleList(roleList);

        return new CustomUserDetails(userVo);
    }
}
