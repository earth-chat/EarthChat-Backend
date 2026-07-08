package com.earth_chat.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidatedField {

    @Schema(description = "필드명")
    private String fieldName;

    @Schema(description = "유효성 검증 메시지")
    private String validatedMessage;
}
