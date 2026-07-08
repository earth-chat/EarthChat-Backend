package com.earth_chat.chatroom.service.impl;

import com.earth_chat.chatroom.controller.request.CreateChatroomRequest;
import com.earth_chat.chatroom.controller.request.UpdateChatroomRequest;
import com.earth_chat.chatroom.controller.response.ChatroomListResponse;
import com.earth_chat.chatroom.controller.response.CreateChatroomResponse;
import com.earth_chat.chatroom.controller.response.UpdateChatroomResponse;
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
import org.springframework.util.StringUtils;

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

        return CreateChatroomResponse.of(chatroomInfo);
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
                .map(ChatroomListResponse::of)
                .toList();

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
        ChatroomVo chatroom = selectChatroomByChatroomSeq(chatroomSeq)
                .orElseThrow(() -> new ChatroomNotFoundException(messageUtil.getMessage(MessageCode.CHATROOM_NOT_FOUND.getCode())));

        UserVo user = userService.selectUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        Long ownerSeq = chatroom.getOwnerSeq();
        if (!ownerSeq.equals(user.getUserSeq())) {
            throw new ChatroomOwnerNotMatchesException(messageUtil.getMessage(MessageCode.CHATROOM_OWNER_NOT_MATCH.getCode()));
        }

        return chatroomMapper.deleteChatroom(chatroomSeq);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateChatroomResponse update(CustomUserDetails customUserDetails, UpdateChatroomRequest request) {

        UserVo user = userService.selectUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage(MessageCode.USER_NOT_FOUND.getCode())));

        ChatroomVo chatroom = selectChatroomByChatroomSeq(request.getChatroomSeq())
                .orElseThrow(() -> new ChatroomNotFoundException(messageUtil.getMessage(MessageCode.CHATROOM_NOT_FOUND.getCode())));

        Long ownerSeq = chatroom.getOwnerSeq();
        if (!ownerSeq.equals(user.getUserSeq())) {
            throw new ChatroomOwnerNotMatchesException(messageUtil.getMessage(MessageCode.CHATROOM_OWNER_NOT_MATCH.getCode()));
        }

        if (StringUtils.hasText(request.getName())) {
            chatroom.setChatroomName(request.getName());
        }

        if (StringUtils.hasText(request.getDescription())) {
            chatroom.setChatroomDescription(request.getDescription());
        }

        if (request.getMaxParticipantNum() != null) {
            chatroom.setMaxParticipantNum(request.getMaxParticipantNum());
        }

        if (StringUtils.hasText(request.getPublicYn())) {
            chatroom.setPublicYn(request.getPublicYn());

            if (request.getPublicYn().equals("N")) {

                if (StringUtils.hasText(request.getRoomPwd())) {
                    chatroom.setRoomPwd(passwordEncoder.encode(request.getRoomPwd()));
                }
            }
        }

        chatroomMapper.updateChatroom(chatroom);

        return UpdateChatroomResponse.of(chatroom);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<ChatroomVo> selectChatroomByChatroomSeq(Long chatroomSeq) {
        return Optional.ofNullable(chatroomMapper.selectChatroomByChatroomSeq(chatroomSeq));
    }
}
