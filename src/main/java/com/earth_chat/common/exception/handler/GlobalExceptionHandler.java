package com.earth_chat.common.exception.handler;

import com.earth_chat.common.exception.*;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    // 400
    
    @ExceptionHandler(value = {
            AlreadyExistsEmailException.class,
            AlreadyExistsNicknameException.class,
            FailSendingMailException.class,
            InvalidEmailAuthNumException.class,
            RequiredEmailAuthException.class,
            InvalidPasswordFindKeyException.class
    })
    public ResponseEntity<ResponseWrapper> badRequest(Exception e) {
        log.error("Bad Request 발생 : ", e);

        return ResponseWrapperUtil.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            PasswordNotMatchesException.class
    })
    public ResponseEntity<ResponseWrapper> passwordNotMatchesException(PasswordNotMatchesException e) {
        log.error("Bad Request 발생 : ", e);

        return ResponseWrapperUtil.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            EmailAuthInfoNotFoundException.class
    })
    public ResponseEntity<ResponseWrapper> notFound(Exception e) {
        log.error("Not Found 발생 : ", e);

        return ResponseWrapperUtil.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 500

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<ResponseWrapper> internalServerError(Exception e) {
        log.error("Internal Server Error : ", e);

        return ResponseWrapperUtil.fail("서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
