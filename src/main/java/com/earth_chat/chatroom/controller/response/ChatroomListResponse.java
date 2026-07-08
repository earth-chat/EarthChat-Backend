package com.earth_chat.chatroom.controller.response;

import com.earth_chat.chatroom.vo.ChatroomVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomListResponse {

    @Schema(description = "채팅방 SEQ")
    private Long chatroomSeq;

    @Schema(description = "채팅방명")
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "방장 SEQ")
    private Long ownerSeq;

    @Schema(description = "방장 닉네임")
    private String ownerNickname;

    @Schema(description = "최대 참여 가능 인원")
    private Integer maxParticipantNum;

    @Schema(description = "현재 참여 인원")
    private Integer currentParticipantNum;

    @Schema(description = "공개 여부")
    private String publicYn;

    @Schema(description = "등록일시")
    private LocalDateTime regDt;

    @Schema(description = "수정일시")
    private LocalDateTime modDt;

    /**
     * 응답 객체 생성.
     * @param chatroomVo 채팅방 정보
     * @return ChatroomListResponse
     */
    public static ChatroomListResponse of(ChatroomVo chatroomVo) {
        return ChatroomListResponse.builder()
                .chatroomSeq(chatroomVo.getChatroomSeq())
                .name(chatroomVo.getChatroomName())
                .description(chatroomVo.getChatroomDescription())
                .ownerSeq(chatroomVo.getOwnerSeq())
                .ownerNickname(chatroomVo.getOwnerNickname())
                .maxParticipantNum(chatroomVo.getMaxParticipantNum())
                .currentParticipantNum(chatroomVo.getCurrentParticipantNum())
                .publicYn(chatroomVo.getPublicYn())
                .regDt(chatroomVo.getRegDt())
                .modDt(chatroomVo.getModDt())
                .build();
    }
}
