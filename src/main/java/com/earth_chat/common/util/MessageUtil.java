package com.earth_chat.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    /**
     * 메시지 조회.
     * @param code 코드
     * @return 메시지
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 조회,
     * @param code 코드
     * @param args 인자
     * @return 메시지
     */
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
