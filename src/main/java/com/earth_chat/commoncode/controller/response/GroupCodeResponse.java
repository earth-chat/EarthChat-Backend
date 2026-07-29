package com.earth_chat.commoncode.controller.response;

import com.earth_chat.commoncode.vo.CommonCodeVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCodeResponse {

    @Schema(description = "그룹 코드")
    private String groupCode;

    @Schema(description = "그룹명")
    private String groupName;

    @Schema(description = "그룹 설명")
    private String groupDescription;

    @Schema(description = "사용 여부")
    private String useYn;

    /**
     * 응답 객체 생성.
     * @param commonCodeVo 공통코드 VO
     * @return GroupCodeResponse
     */
    public static GroupCodeResponse of(CommonCodeVo commonCodeVo) {
        return GroupCodeResponse.builder()
                .groupCode(commonCodeVo.getGroupCode())
                .groupName(commonCodeVo.getGroupName())
                .groupDescription(commonCodeVo.getGroupDescription())
                .useYn(commonCodeVo.getUseYn())
                .build();
    }
}
