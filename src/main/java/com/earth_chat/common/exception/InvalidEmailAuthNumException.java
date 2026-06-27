package com.earth_chat.common.exception;

public class InvalidEmailAuthNumException extends RuntimeException {

    public InvalidEmailAuthNumException() {
        super();
    }

    public InvalidEmailAuthNumException(String message) {
        super(message);
    }
}
