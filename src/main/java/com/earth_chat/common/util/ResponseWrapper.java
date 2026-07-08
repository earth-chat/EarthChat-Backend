package com.earth_chat.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseWrapper {

    @Schema(description = "HTTP 상태 코드", example = "200")
    private int status;

    @Schema(description = "유효성 검증 객체")
    private List<ValidatedField> validatedFields;

    @Schema(description = "메시지", example = "success")
    private String message;

    @Schema(description = "결과 데이터")
    private Object result;
}
