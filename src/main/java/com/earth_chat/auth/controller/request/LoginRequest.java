package com.earth_chat.auth.controller.request;

import com.earth_chat.common.enums.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "로그인 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LoginType loginType;
}
