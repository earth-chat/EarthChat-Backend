package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.mapper.PasswordFindKeyMapper;
import com.earth_chat.auth.service.PasswordFindKeyService;
import com.earth_chat.auth.vo.PasswordFindKeyVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordFindKeyServiceImpl implements PasswordFindKeyService {

    private final PasswordFindKeyMapper passwordFindKeyMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertPasswordFindKey(PasswordFindKeyVo passwordFindKeyVo) {
        return passwordFindKeyMapper.insertPasswordFindKey(passwordFindKeyVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordFindKeyVo> selectPasswordFindKeyByEmail(String email) {
        return Optional.ofNullable(passwordFindKeyMapper.selectPasswordFindKeyByEmail(email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updatePasswordFindKey(PasswordFindKeyVo passwordFindKeyVo) {
        return passwordFindKeyMapper.updatePasswordFindKey(passwordFindKeyVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordFindKeyVo> selectPasswordFindKeyByKey(String key) {
        return Optional.ofNullable(passwordFindKeyMapper.selectPasswordFindKeyByKey(key));
    }
}
