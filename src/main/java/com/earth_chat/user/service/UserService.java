package com.earth_chat.user.service;

import com.earth_chat.user.vo.UserVo;

public interface UserService {

    /**
     * 이메일 존재 여부 확인.
     * @param email 이메일
     * @return boolean
     */
    boolean existsEmail(String email);

    /**
     * 닉네임 존재 여부 확인.
     * @param nickname 닉네임
     * @return boolean
     */
    boolean existsNickname(String nickname);

    /**
     * 사용자 데이터 삽입.
     * @param userVo 사용자 데이터
     * @return int
     */
    int insertUser(UserVo userVo);
}
