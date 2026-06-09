package com.earth_chat.auth.controller;

import com.earth_chat.auth.controller.request.RegisterRequest;
import com.earth_chat.auth.service.AuthService;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller API", description = "사용자 인증 관련 API Controller")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 API", description = "사용자 회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "이미 가입된 닉네임 or 이메일일 경우 발생")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "회원가입 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterRequest.class)
                    )
            )
            @RequestBody RegisterRequest request
    ) {
        return ResponseWrapperUtil.success("success", authService.register(request));
    }
}
