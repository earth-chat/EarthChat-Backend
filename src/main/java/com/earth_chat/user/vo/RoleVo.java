package com.earth_chat.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleVo {

    // 역할 SEQ
    private Long roleSeq;

    // 역할명
    private String roleName;

    // 관리자 여부
    private String adminYn;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
