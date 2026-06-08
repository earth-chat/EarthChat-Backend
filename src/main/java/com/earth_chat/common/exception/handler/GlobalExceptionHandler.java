package com.earth_chat.common.exception.handler;

import com.earth_chat.common.exception.AlreadyExistsEmailException;
import com.earth_chat.common.exception.AlreadyExistsNicknameException;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    // 400
    
    @ExceptionHandler(value = {
            AlreadyExistsEmailException.class,
            AlreadyExistsNicknameException.class
    })
    public ResponseEntity<ResponseWrapper> badRequest(Exception e) {
        log.error("Bad Request 발생 : ", e);

        return ResponseWrapperUtil.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
