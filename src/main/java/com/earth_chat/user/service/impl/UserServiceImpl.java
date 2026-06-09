package com.earth_chat.user.service.impl;

import com.earth_chat.user.mapper.UserMapper;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
