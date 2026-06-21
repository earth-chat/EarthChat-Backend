package com.earth_chat.common.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
