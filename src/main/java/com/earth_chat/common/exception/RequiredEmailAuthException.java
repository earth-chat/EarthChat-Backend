package com.earth_chat.common.exception;

public class RequiredEmailAuthException extends RuntimeException {

    public RequiredEmailAuthException() {
        super();
    }

    public RequiredEmailAuthException(String message) {
        super(message);
    }
}
