package com.earth_chat.auth.controller;

import com.earth_chat.auth.controller.request.*;
import com.earth_chat.auth.service.AuthService;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller API", description = "사용자 인증 관련 API Controller")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "이메일 중복 확인 API", description = "이메일 중복 확인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping("/exists-email")
    public ResponseEntity<ResponseWrapper> existsEmail(
            @Parameter(required = true, description = "이메일")
            @RequestParam(required = true) String email
    ) {
        return ResponseWrapperUtil.success("success", authService.existsEmail(email));
    }

    @Operation(summary = "닉네임 중복 확인 API", description = "닉네임 중복 확인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content ={
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping("/exists-nickname")
    public ResponseEntity<ResponseWrapper> existsNickname(
            @Parameter(required = true, description = "닉네임")
            @RequestParam(required = true) String nickname
    ) {
        return ResponseWrapperUtil.success("success", authService.existsNickname(nickname));
    }

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
            @Validated @RequestBody RegisterRequest request
    ) {
        return ResponseWrapperUtil.success("success", authService.register(request));
    }

    @Operation(summary = "로그인 API", description = "사용자 로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "일치하지 않는 비밀번호일 경우 발생"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자를 찾지 못할 경우 발생"),
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "로그인 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
            @Validated @RequestBody LoginRequest loginRequest
    ) {
        return ResponseWrapperUtil.success("success", authService.login(loginRequest));
    }

    @Operation(summary = "인증 이메일 발송 API", description = "인증 이메일 발송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "이메일 발송 실패 시 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "비밀번호 찾기 메일 발송 시 해당 사용자를 찾지 못했을 시 반환")
    })
    @PostMapping("/email")
    public ResponseEntity<ResponseWrapper> email(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "이메일 발송 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SendMailRequest.class)
                    )
            )
            @Validated @RequestBody SendMailRequest request
    ) {
        authService.sendMail(request);
        return ResponseWrapperUtil.success("success");
    }

    @Operation(summary = "이메일 인증 코드 검증 API", description = "이메일 인증 코드 검증 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "인증 코드가 만료되었거나 일치하지 않을 경우 발생"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 정보를 찾지 못했을 경우 발생")
    })
    @PostMapping("/email/auth-code")
    public ResponseEntity<ResponseWrapper> validateEmailAuthCode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "인증코드 검증 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidateMailAuthCodeRequest.class)
                    )
            )
            @Validated @RequestBody ValidateMailAuthCodeRequest request
    ) {
        return ResponseWrapperUtil.success("success", authService.validateMailAuthCode(request));
    }

    @Operation(summary = "비밀번호 초기화 API", description = "비밀번호 찾기에서 사용되는 비밀번호 초기화 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "키값이 만료되었을 경우 발생"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 혹은 키값을 찾지 못했을 경우 발생")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper> resetPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "비밀번호 초기화 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PasswordResetRequest.class)
                    )
            )
            @Validated @RequestBody PasswordResetRequest request
    ) {
        authService.resetPassword(request);
        return ResponseWrapperUtil.success("success");
    }

    @Operation(summary = "토큰 재발급 API", description = "토큰 재발급 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "토큰이 만료되었을 경우 발생"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 혹은 토큰값을 찾지 못했을 경우 발생")
    })
    @PostMapping("/refresh")
    public ResponseEntity<ResponseWrapper> refresh(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "토큰 재발급 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TokenRefreshRequest.class)
                    )
            )
            @Validated @RequestBody TokenRefreshRequest request
    ) {
        return ResponseWrapperUtil.success("success", authService.refresh(request));
    }

    @Operation(summary = "로그아웃 API", description = "로그아웃 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자를 찾지 못했을 경우 발생")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name="Jwt Auth")
    @PostMapping("/logout")
    public ResponseEntity<ResponseWrapper> logout(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        authService.logout(customUserDetails);
        return ResponseWrapperUtil.success("success");
    }
}
