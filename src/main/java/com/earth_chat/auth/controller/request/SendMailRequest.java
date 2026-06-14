package com.earth_chat.auth.controller.request;

import com.earth_chat.common.enums.MailType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequest {

    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "메일 유형 - REGISTER: 회원가입, PASSWORD: 비밀번호 찾기", requiredMode = Schema.RequiredMode.REQUIRED)
    private MailType type;
}
