package com.earth_chat.user.mapper;

import com.earth_chat.user.vo.RoleVo;
import com.earth_chat.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 이메일 존재 여부 확인.
     * @param email 이메일
     * @return boolean
     */
    boolean existsEmail(@Param("email") String email);

    /**
     * 닉네임 존재 여부 확인.
     * @param nickname 닉네임
     * @return boolean
     */
    boolean existsNickname(@Param("nickname") String nickname);

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
    UserVo selectUserByEmail(@Param("email") String email);

    /**
     * 이메일을 통한 사용자 데이터 조회.
     * @param userSeq 사용자 SEQ
     * @return 사용자 데이터
     */
    UserVo selectUserByUserSeq(@Param("userSeq") Long userSeq);

    /**
     * 사용자 역할 리스트 조회.
     * @param userSeq 사용자 SEQ
     * @return List<RoleVo>
     */
    List<RoleVo> selectRolesByUserSeq(@Param("userSeq") Long userSeq);

    /**
     * 사용자 할당용 역할 리스트 조회.
     * @return List<RoleVo>
     */
    List<RoleVo> selectUserRoles();

    /**
     * 사용자 역할 삽입.
     * @param userVo 사용자 데이터
     * @return int
     */
    int insertUserRole(UserVo userVo);

    /**
     * 사용자 정보 업데이트.
     * @param userVo 사용자 정보
     */
    int updateUserInfo(UserVo userVo);

    /**
     * 사용자 비밀번호 업데이트.
     * @param userVo 사용자 정보
     * @return int
     */
    int updateUserPassword(UserVo userVo);
}
