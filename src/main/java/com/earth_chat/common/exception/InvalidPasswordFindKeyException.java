package com.earth_chat.common.exception;

public class InvalidPasswordFindKeyException extends RuntimeException {

    public InvalidPasswordFindKeyException() {
        super();
    }

    public InvalidPasswordFindKeyException(String message) {
        super(message);
    }
}
