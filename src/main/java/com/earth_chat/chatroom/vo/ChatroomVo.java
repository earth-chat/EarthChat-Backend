package com.earth_chat.chatroom.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomVo {

    // 채팅방 SEQ
    private Long chatroomSeq;

    // 채팅방명
    private String chatroomName;

    // 설명
    private String chatroomDescription;

    // 방장 SEQ
    private Long ownerSeq;

    // 최대 참여 가능 인원
    private Integer maxParticipantNum;

    // 사용 여부
    private String useYn;

    // 채팅방 공개 여부
    private String publicYn;

    // 채팅방 비밀번호
    private String roomPwd;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
