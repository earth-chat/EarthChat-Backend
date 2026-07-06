package com.earth_chat.chatroom.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomParticipantVo {

    // 참여자 SEQ
    private Long participantSeq;

    // 채팅방 SEQ
    private Long chatroomSeq;

    // 참여자 유형
    private String participantType;

}
