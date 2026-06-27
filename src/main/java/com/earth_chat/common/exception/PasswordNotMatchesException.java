package com.earth_chat.common.exception;

public class PasswordNotMatchesException extends RuntimeException {

    public PasswordNotMatchesException() {
        super();
    }

    public PasswordNotMatchesException(String message) {
        super(message);
    }
}
