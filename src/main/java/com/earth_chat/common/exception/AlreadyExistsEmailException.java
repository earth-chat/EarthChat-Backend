package com.earth_chat.common.exception;

public class AlreadyExistsEmailException extends RuntimeException {

    public AlreadyExistsEmailException() {
        super();
    }

    public AlreadyExistsEmailException(String message) {
        super(message);
    }
}
