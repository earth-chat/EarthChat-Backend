package com.earth_chat.auth.mapper;

import com.earth_chat.auth.vo.PasswordFindKeyVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordFindKeyMapper {

    int insertPasswordFindKey(PasswordFindKeyVo passwordFindKeyVo);
}
