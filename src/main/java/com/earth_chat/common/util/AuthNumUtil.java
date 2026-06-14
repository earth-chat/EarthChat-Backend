package com.earth_chat.common.util;

import java.security.SecureRandom;

public class AuthNumUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 랜덤 인증번호 생성.
     * @param length 문자열 길이
     * @return 랜덤 인증번호
     */
    public static String generateAuthNum(int length) {
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }

        return builder.toString();
    }
}
