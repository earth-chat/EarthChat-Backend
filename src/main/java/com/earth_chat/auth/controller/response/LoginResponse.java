package com.earth_chat.auth.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Refresh Token")
    private String refreshToken;

    @Schema(description = "사용자 SEQ")
    private Long userSeq;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "번역 코드")
    private String translateCode;
}
