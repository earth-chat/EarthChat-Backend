package com.earth_chat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageCode {

    // 사용자 관련 Message Code
    USER_NOT_FOUND("user.not-found"),
    USER_DUPLICATE_EMAIL("user.duplicate-email"),
    USER_DUPLICATE_NICKNAME("user.duplicate-nickname"),
    USER_INVALID_PASSWORD("user.invalid-password"),
    USER_REQUIRED_EMAIL_AUTH("user.require-email-auth"),

    // 채팅방 관련 Message Code
    CHATROOM_NOT_FOUND("chatroom.not-found"),
    CHATROOM_OWNER_NOT_MATCH("chatroom.owner.not-match"),

    // 인증코드 관련 Message Code
    AUTH_CODE_INVALID("auth-code.invalid"),
    AUTH_CODE_EXPIRED("auth-code.expired"),

    // 비밀번호 찾기 키 관련 Message Code
    PASSWORD_KEY_INVALID("password-key.invalid"),
    PASSWORD_KEY_EXPIRED("password-key.expired"),

    // Refresh Token 관련 Message Code
    REFRESH_TOKEN_NOT_FOUND("refresh-token.not-found"),
    REFRESH_TOKEN_EXPIRED("refresh-token.expired"),

    // 인증 관련 Message Code
    AUTH_UNAUTHORIZED("auth.unauthorized"),

    // 서버 Message Code
    INTERNAL_SERVER_ERROR("server.internal-server-error");

    private final String code;
}
