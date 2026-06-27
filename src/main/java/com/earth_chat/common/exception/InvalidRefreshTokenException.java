package com.earth_chat.common.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super();
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
