package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.mapper.EmailAuthInfoMapper;
import com.earth_chat.auth.service.EmailAuthInfoService;
import com.earth_chat.auth.vo.EmailAuthInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthInfoServiceImpl implements EmailAuthInfoService {

    private final EmailAuthInfoMapper emailAuthInfoMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertEmailAuthInfo(EmailAuthInfoVo emailAuthInfoVo) {
        return emailAuthInfoMapper.insertEmailAuthInfo(emailAuthInfoVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailAuthInfoVo selectEmailAuthInfoByEmail(String email) {
        return emailAuthInfoMapper.selectEmailAuthInfoByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateEmailAuthInfo(EmailAuthInfoVo authInfo) {
        return emailAuthInfoMapper.updateEmailAuthInfo(authInfo);
    }
}
