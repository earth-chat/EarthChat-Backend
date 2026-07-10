package com.earth_chat.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatHistoryVo {

    // 채팅 이력 SEQ
    private Long chatHistorySeq;

    // 채팅방 SEQ
    private Long chatroomSeq;

    // 송신자 SEQ
    private Long senderSeq;

    // 송신자 유형
    private String senderType;

    // 원본 메시지
    private String originalMessage;

    // 정제 메시지
    private String processMessage;

    // 등록일시
    private LocalDateTime regDt;

    // 수정일시
    private LocalDateTime modDt;
}
