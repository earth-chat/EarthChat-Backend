package com.earth_chat.chatroom.controller.response;

import com.earth_chat.chatroom.vo.ChatroomVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChatroomResponse {

    @Schema(description = "채팅방 SEQ")
    private Long chatroomSeq;

    @Schema(description = "채팅방명")
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "방장 SEQ")
    private Long ownerSeq;

    @Schema(description = "최대 참여 가능 인원")
    private Integer maxParticipantNum;

    @Schema(description = "공개 여부")
    private String publicYn;

    /**
     * 응답 객체 생성.
     * @param chatroomVo 채팅방 정보
     * @return CreateChatroomResponse
     */
    public static CreateChatroomResponse of(ChatroomVo chatroomVo) {
        return CreateChatroomResponse.builder()
                .chatroomSeq(chatroomVo.getChatroomSeq())
                .name(chatroomVo.getChatroomName())
                .description(chatroomVo.getChatroomDescription())
                .ownerSeq(chatroomVo.getOwnerSeq())
                .maxParticipantNum(chatroomVo.getMaxParticipantNum())
                .publicYn(chatroomVo.getPublicYn())
                .build();
    }
}
