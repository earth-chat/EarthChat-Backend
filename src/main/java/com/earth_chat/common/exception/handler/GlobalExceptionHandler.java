package com.earth_chat.common.exception.handler;

import com.earth_chat.common.enums.MessageCode;
import com.earth_chat.common.exception.*;
import com.earth_chat.common.util.MessageUtil;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import com.earth_chat.common.util.ValidatedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;

    // 400

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseWrapper> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidatedField> validatedFields = e.getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();

                    return ValidatedField.builder()
                            .fieldName(fieldName)
                            .validatedMessage(error.getDefaultMessage())
                            .build();
                }).toList();

        return ResponseWrapperUtil.fail("fail", validatedFields, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            AlreadyExistsEmailException.class,
            AlreadyExistsNicknameException.class,
            FailSendingMailException.class,
            InvalidEmailAuthNumException.class,
            RequiredEmailAuthException.class,
            InvalidPasswordFindKeyException.class,
            InvalidRefreshTokenException.class,
            ChatroomOwnerNotMatchesException.class
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
            EmailAuthInfoNotFoundException.class,
            RefreshTokenNotFoundException.class,
            PasswordFindKeyNotFoundException.class,
            ChatroomNotFoundException.class
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

        return ResponseWrapperUtil.fail(messageUtil.getMessage(MessageCode.INTERNAL_SERVER_ERROR.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
