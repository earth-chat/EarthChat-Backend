package com.earth_chat.user.vo;

import com.earth_chat.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVo {

    // 사용자 SEQ
    private Long userSeq;

    // 닉네임
    private String nickname;

    // 이메일
    private String email;

    // 비밀번호
    private String pwd;

    // 사용 여부
    private String useYn;

    // 번역 코드
    private String translateCode;

    // 계정 상태
    private UserStatus userStatus;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;

    // 역할 목록
    private List<RoleVo> roleList;
}
