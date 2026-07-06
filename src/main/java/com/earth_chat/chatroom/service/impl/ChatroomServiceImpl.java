package com.earth_chat.chatroom.service.impl;

import com.earth_chat.chatroom.controller.request.CreateChatroomRequest;
import com.earth_chat.chatroom.controller.response.ChatroomListResponse;
import com.earth_chat.chatroom.controller.response.CreateChatroomResponse;
import com.earth_chat.chatroom.mapper.ChatroomMapper;
import com.earth_chat.chatroom.service.ChatroomService;
import com.earth_chat.chatroom.vo.ChatroomParticipantVo;
import com.earth_chat.chatroom.vo.ChatroomVo;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.ChatroomSearchFiltering;
import com.earth_chat.common.enums.MessageCode;
import com.earth_chat.common.exception.ChatroomNotFoundException;
import com.earth_chat.common.exception.ChatroomOwnerNotMatchesException;
import com.earth_chat.common.util.MessageUtil;
import com.earth_chat.common.util.PaginationResponse;
import com.earth_chat.user.service.UserService;
import com.earth_chat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomMapper chatroomMapper;
    private final UserService userService;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CreateChatroomResponse create(CustomUserDetails customUserDetails, CreateChatroomRequest request) {
        UserVo user = userService.selectUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        String publicYn = request.getPublicYn();
        Long ownerSeq = user.getUserSeq();

        ChatroomVo chatroomInfo = null;

        if (publicYn != null && publicYn.equals("N")) {
            chatroomInfo = ChatroomVo.builder()
                    .chatroomName(request.getName())
                    .chatroomDescription(request.getDescription())
                    .maxParticipantNum(request.getMaxParticipantNum())
                    .ownerSeq(ownerSeq)
                    .publicYn(publicYn)
                    .roomPwd(passwordEncoder.encode(request.getRoomPwd()))
                    .build();
        } else {
            chatroomInfo = ChatroomVo.builder()
                    .chatroomName(request.getName())
                    .chatroomDescription(request.getDescription())
                    .maxParticipantNum(request.getMaxParticipantNum())
                    .ownerSeq(ownerSeq)
                    .publicYn(publicYn)
                    .build();
        }

        chatroomMapper.insertChatroom(chatroomInfo);

        ChatroomParticipantVo chatroomParticipant = ChatroomParticipantVo.builder()
                .chatroomSeq(chatroomInfo.getChatroomSeq())
                .participantSeq(chatroomInfo.getOwnerSeq())
                .participantType("USER") // TODO: Enum 변경 예정
                .build();

        chatroomMapper.insertChatroomParticipant(chatroomParticipant);

        return CreateChatroomResponse.builder()
                .chatroomSeq(chatroomInfo.getChatroomSeq())
                .name(chatroomInfo.getChatroomName())
                .description(chatroomInfo.getChatroomDescription())
                .ownerSeq(chatroomInfo.getOwnerSeq())
                .maxParticipantNum(chatroomInfo.getMaxParticipantNum())
                .publicYn(chatroomInfo.getPublicYn())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResponse<List<ChatroomListResponse>> list(int page, int size, String text, ChatroomSearchFiltering filter) {

        Long totalCount = Optional.ofNullable(chatroomMapper.selectTotalCountAsPagination(page, size, text, filter))
                .orElse(0L);

        List<ChatroomVo> chatroomList = chatroomMapper.selectChatroomAsPagination(page, size, text, filter);
        List<ChatroomListResponse> content = chatroomList.stream()
                .map(chatroom -> {
                    return ChatroomListResponse.builder()
                            .chatroomSeq(chatroom.getChatroomSeq())
                            .name(chatroom.getChatroomName())
                            .description(chatroom.getChatroomDescription())
                            .ownerSeq(chatroom.getOwnerSeq())
                            .ownerNickname(chatroom.getOwnerNickname())
                            .maxParticipantNum(chatroom.getMaxParticipantNum())
                            .currentParticipantNum(chatroom.getCurrentParticipantNum())
                            .publicYn(chatroom.getPublicYn())
                            .regDt(chatroom.getRegDt())
                            .modDt(chatroom.getModDt())
                            .build();
                }).toList();

        Long totalPage = (totalCount + size - 1) / size;

        return PaginationResponse.<List<ChatroomListResponse>>builder()
                .page(page)
                .size(size)
                .totalSize(totalCount)
                .totalPage(totalPage)
                .content(content)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(CustomUserDetails customUserDetails, Long chatroomSeq) {
        ChatroomVo chatroom = Optional.ofNullable(chatroomMapper.selectChatroomByChatroomSeq(chatroomSeq))
                .orElseThrow(() -> new ChatroomNotFoundException(messageUtil.getMessage(MessageCode.CHATROOM_NOT_FOUND.getCode())));

        UserVo owner = userService.selectUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        Long ownerSeq = chatroom.getOwnerSeq();
        if (!ownerSeq.equals(owner.getUserSeq())) {
            throw new ChatroomOwnerNotMatchesException(messageUtil.getMessage(MessageCode.CHATROOM_OWNER_NOT_MATCH.getCode()));
        }

        return chatroomMapper.deleteChatroom(chatroomSeq);
    }


}
