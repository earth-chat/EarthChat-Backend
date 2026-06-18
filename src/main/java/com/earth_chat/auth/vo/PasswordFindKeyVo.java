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
public class PasswordFindKeyVo {

    // 키 SEQ
    private Long keySeq;

    // 이메일
    private String email;

    // 키값
    private String keyValue;

    // 사용 여부
    private String useYn;

    // 만기일시
    private LocalDateTime expiredDt;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
