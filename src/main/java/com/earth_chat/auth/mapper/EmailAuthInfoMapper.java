package com.earth_chat.auth.mapper;

import com.earth_chat.auth.vo.EmailAuthInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailAuthInfoMapper {

    int insertEmailAuthInfo(EmailAuthInfoVo emailAuthInfoVo);

    EmailAuthInfoVo selectEmailAuthInfoByEmail(String email);

    int updateEmailAuthInfo(EmailAuthInfoVo authInfo);
}
