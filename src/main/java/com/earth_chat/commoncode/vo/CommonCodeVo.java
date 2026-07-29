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
public class CommonCodeVo {

    // 그룹 코드
    private String groupCode;

    // 그룹명
    private String groupName;

    // 그룹설명
    private String groupDescription;

    // 사용 여부
    private String useYn;

    // 등록 일시
    private LocalDateTime regDt;

    // 수정 일시
    private LocalDateTime modDt;
}
