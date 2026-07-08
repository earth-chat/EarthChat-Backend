package com.earth_chat.auth.controller.response;

import com.earth_chat.common.enums.UserStatus;
import com.earth_chat.user.vo.UserVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

    @Schema(description = "사용자 SEQ")
    private Long userSeq;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "계정 상태")
    private UserStatus userStatus;

    @Schema(description = "번역 코드")
    private String translateCode;

    /**
     * 응답 객체 생성.
     * @param userVo 사용자 정보
     * @return RegisterResponse
     */
    public static RegisterResponse of(UserVo userVo) {
        return RegisterResponse.builder()
                .userSeq(userVo.getUserSeq())
                .nickname(userVo.getNickname())
                .email(userVo.getEmail())
                .userStatus(userVo.getUserStatus())
                .translateCode(userVo.getTranslateCode())
                .build();
    }
}
