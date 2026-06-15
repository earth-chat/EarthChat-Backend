package com.earth_chat.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAuthInfoVo {

    // 메일 인증 코드 SEQ
    private Long mailAuthSeq;

    // 이메일
    private String email;

    // 인증번호
    private String authNum;

    // 인증 여부
    private String authYn;

    // 사용 여부
    private String useYn;

    // 만료일시
    private LocalDateTime expiredDt;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
