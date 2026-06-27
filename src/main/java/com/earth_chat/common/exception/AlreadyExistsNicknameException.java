package com.earth_chat.common.exception;

public class AlreadyExistsNicknameException extends RuntimeException {

    public AlreadyExistsNicknameException() {
        super();
    }

    public AlreadyExistsNicknameException(String message) {
        super(message);
    }
}
