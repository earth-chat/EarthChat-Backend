package com.earth_chat.commoncode.controller.response;

import com.earth_chat.commoncode.vo.CommonCodeDetailVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonCodeDetailResponse {

    @Schema(description = "그룹 코드")
    private String groupCode;

    @Schema(description = "코드")
    private String code;

    @Schema(description = "코드명")
    private String codeName;

    @Schema(description = "코드 설명")
    private String codeDescription;

    @Schema(description = "사용 여부")
    private String useYn;

    /**
     * 응답 객체 생성.
     * @param commonCodeDetailVo 공통코드 상세 VO
     * @return CommonCodeDetailResponse
     */
    public static CommonCodeDetailResponse of(CommonCodeDetailVo commonCodeDetailVo) {
        return CommonCodeDetailResponse.builder()
                .groupCode(commonCodeDetailVo.getGroupCode())
                .code(commonCodeDetailVo.getCode())
                .codeName(commonCodeDetailVo.getCodeName())
                .codeDescription(commonCodeDetailVo.getCodeDescription())
                .useYn(commonCodeDetailVo.getUseYn())
                .build();
    }
}
