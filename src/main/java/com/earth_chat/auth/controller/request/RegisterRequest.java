package com.earth_chat.auth.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "번역 코드", requiredMode = Schema.RequiredMode.REQUIRED)
    private String translateCode;
}
