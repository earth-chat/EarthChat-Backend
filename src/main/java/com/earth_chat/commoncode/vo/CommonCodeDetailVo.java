package com.earth_chat.commoncode.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonCodeDetailVo {

    // 그룹 코드
    private String groupCode;

    // 코드
    private String code;

    // 코드명
    private String codeName;

    // 코드 설명
    private String codeDescription;

    // 사용 여부
    private String useYn;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
