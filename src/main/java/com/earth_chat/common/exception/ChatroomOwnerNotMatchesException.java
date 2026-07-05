package com.earth_chat.common.exception;

public class ChatroomOwnerNotMatchesException extends RuntimeException {

    public ChatroomOwnerNotMatchesException() {
        super();
    }

    public ChatroomOwnerNotMatchesException(String message) {
        super(message);
    }
}
