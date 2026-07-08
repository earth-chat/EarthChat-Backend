package com.earth_chat.auth.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest {

    @Schema(description = "Refresh Token", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String token;
}
