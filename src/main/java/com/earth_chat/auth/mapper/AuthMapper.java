package com.earth_chat.auth.mapper;

import com.earth_chat.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

    boolean existsEmail(@Param("email") String email);

    boolean existsNickname(@Param("nickname") String nickname);

    int insertUser(UserVo userVo);
}
