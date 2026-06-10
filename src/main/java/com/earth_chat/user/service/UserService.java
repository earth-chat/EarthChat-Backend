package com.earth_chat.user.service;

import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;

import java.util.List;

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

    /**
     * 이메일을 통한 사용자 데이터 조회.
     * @param email 이메일
     * @return 사용자 데이터
     */
    UserVo selectUserByEmail(String email);

    /**
     * 사용자 역할 리스트 조회.
     * @param userSeq 사용자 SEQ
     * @return List<RoleVo>
     */
    List<RoleVo> selectRolesByUserSeq(Long userSeq);
}
