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
public class RefreshTokenVo {

    // 토큰 SEQ
    private Long tokenSeq;

    // 사용자 SEQ
    private Long userSeq;

    // Refresh Token
    private String tokenValue;

    // 만료일시
    private LocalDateTime expiredDt;

    // 사용 여부
    private String useYn;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;

}
