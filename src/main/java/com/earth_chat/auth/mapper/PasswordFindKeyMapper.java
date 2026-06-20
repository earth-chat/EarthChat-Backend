package com.earth_chat.auth.mapper;

import com.earth_chat.auth.vo.PasswordFindKeyVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordFindKeyMapper {

    /**
     * 비밀번호 찾기 키 삽입.
     * @param passwordFindKeyVo 비밀번호 찾기 키 정보
     * @return int
     */
    int insertPasswordFindKey(PasswordFindKeyVo passwordFindKeyVo);

    /**
     * 비밀번호 찾기 키 조회.
     * @param email 이메일
     * @return 비밀번호 찾기 키
     */
    PasswordFindKeyVo selectPasswordFindKeyByEmail(String email);

    /**
     * 비밀번호 찾기 키 업데이트.
     * @param passwordFindKeyVo 비밀번호 찾기 키
     * @return int
     */
    int updatePasswordFindKey(PasswordFindKeyVo passwordFindKeyVo);
}
