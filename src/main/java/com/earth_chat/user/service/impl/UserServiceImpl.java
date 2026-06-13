package com.earth_chat.user.service.impl;

import com.earth_chat.user.mapper.UserMapper;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsEmail(String email) {
        return userMapper.existsEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsNickname(String nickname) {
        return userMapper.existsNickname(nickname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertUser(UserVo userVo) {
        return userMapper.insertUser(userVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVo selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleVo> selectRolesByUserSeq(Long userSeq) {
        return userMapper.selectRolesByUserSeq(userSeq);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleVo> selectUserRoles() {
        return userMapper.selectUserRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertUserRole(UserVo userVo) {
        return userMapper.insertUserRole(userVo);
    }
}
