package com.earth_chat.common.exception;

public class FailSendingMailException extends RuntimeException {

    public FailSendingMailException() {
        super();
    }

    public FailSendingMailException(String message) {
        super(message);
    }
}
