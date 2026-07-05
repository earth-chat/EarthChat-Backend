package com.earth_chat.common.exception;

public class ChatroomNotFoundException extends RuntimeException {

    public ChatroomNotFoundException() {
        super();
    }

    public ChatroomNotFoundException(String message) {
        super(message);
    }
}
