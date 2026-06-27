package com.earth_chat.common.exception;

public class PasswordFindKeyNotFoundException extends RuntimeException {

    public PasswordFindKeyNotFoundException() {
        super();
    }

    public PasswordFindKeyNotFoundException(String message) {
        super(message);
    }
}
