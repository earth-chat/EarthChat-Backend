package com.earth_chat.chatroom.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatroomRequest {

    @Schema(description = "채팅방명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "설명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "최대 참여 가능 인원", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer maxParticipantNum;

    @Schema(description = "공개 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String publicYn;

    @Schema(description = "채팅방 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String roomPwd;
}
