package com.earth_chat.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseWrapperUtil {

    /**
     * 요청 성공 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> success(String message) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .result(null)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    /**
     * 요청 성공 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @param result 결과 데이터
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> success(String message, Object result) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .result(result)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    /**
     * 요청 실패 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> fail(String message) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .result(null)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 요청 실패 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @param result 결과 데이터
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> fail(String message, Object result) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .result(result)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 요청 실패 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @param httpStatus HTTP 상태 코드
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> fail(String message, HttpStatus httpStatus) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(httpStatus.value())
                .message(message)
                .result(null)
                .build();

        return new ResponseEntity<>(responseWrapper, httpStatus);
    }

    /**
     * 요청 실패 응답 객체 생성 Util 메서드.
     * @param message 메시지
     * @param httpStatus HTTP 상태 코드
     * @param result 결과 데이터
     * @return ResponseEntity<ResponseWrapper>
     */
    public static ResponseEntity<ResponseWrapper> fail(String message, HttpStatus httpStatus, Object result) {
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .status(httpStatus.value())
                .message(message)
                .result(result)
                .build();

        return new ResponseEntity<>(responseWrapper, httpStatus);
    }
}
