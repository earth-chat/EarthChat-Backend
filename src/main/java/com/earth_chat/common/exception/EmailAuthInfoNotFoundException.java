package com.earth_chat.common.exception;

public class EmailAuthInfoNotFoundException extends RuntimeException {

    public EmailAuthInfoNotFoundException() {
        super();
    }

    public EmailAuthInfoNotFoundException(String message) {
        super(message);
    }
}
