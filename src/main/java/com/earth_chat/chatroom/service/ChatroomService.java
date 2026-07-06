package com.earth_chat.chatroom.service;

import com.earth_chat.chatroom.controller.request.CreateChatroomRequest;
import com.earth_chat.chatroom.controller.response.ChatroomListResponse;
import com.earth_chat.chatroom.controller.response.CreateChatroomResponse;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.ChatroomSearchFiltering;
import com.earth_chat.common.util.PaginationResponse;

import java.util.List;

public interface ChatroomService {

    /**
     * 채팅방 생성.
     * @param customUserDetails 인증된 사용자 객체
     * @param request 채팅방 생성 요청 객체
     * @return 채팅방 정보
     */
    CreateChatroomResponse create(CustomUserDetails customUserDetails, CreateChatroomRequest request);

    /**
     * 채팅방 목록 조회.
     * @param page 페이지 번호
     * @param size 데이터 개수
     * @param text 채팅방명 또는 채팅방 설명
     * @param filter 필터링 조건
     * @return PaginationResponse<List<ChatroomListResponse>>
     */
    PaginationResponse<List<ChatroomListResponse>> list(int page, int size, String text, ChatroomSearchFiltering filter);

    /**
     * 채팅방 삭제.
     * @param customUserDetails 인증된 사용자 객체
     * @param chatroomSeq 채팅방 SEQ
     */
    int delete(CustomUserDetails customUserDetails, Long chatroomSeq);
}
