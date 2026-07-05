package com.earth_chat.chatroom.mapper;

import com.earth_chat.chatroom.vo.ChatroomVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatroomMapper {

    /**
     * 채팅방 INSERT.
     * @param chatroomVo 채팅방 정보
     * @return int
     */
    int insertChatroom(ChatroomVo chatroomVo);

    /**
     * 채팅방 목록 조회.
     * @param page 페이지 번호
     * @param size 데이터 개수
     * @return List<ChatroomVo>
     */
    List<ChatroomVo> selectChatroomAsPagination(int page, int size);

    /**
     * 채팅방 목록 전체 데이터 개수 조회.
     * @param page 페이지 번호
     * @param size 데이터 개수
     * @return int
     */
    long selectTotalCountAsPagination(int page, int size);

    /**
     * 채팅방 조회.
     * @param chatroomSeq 채팅방 SEQ
     * @return ChatroomVo
     */
    ChatroomVo selectChatroomByChatroomSeq(Long chatroomSeq);

    /**
     * 채팅방 삭제.
     * @param chatroomSeq 채팅방 SEQ
     * @return int
     */
    int deleteChatroom(Long chatroomSeq);
}
