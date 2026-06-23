package com.earth_chat.user.service.impl;

import com.earth_chat.user.mapper.UserMapper;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<UserVo> selectUserByEmail(String email) {
        return Optional.ofNullable(userMapper.selectUserByEmail(email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserVo> selectUserByUserSeq(Long userSeq) {
        return Optional.ofNullable(userMapper.selectUserByUserSeq(userSeq));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateUserPassword(UserVo userVo) {
        return userMapper.updateUserPassword(userVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateUserInfo(UserVo userVo) {
        return userMapper.updateUserInfo(userVo);
    }
}
