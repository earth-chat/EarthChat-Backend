package com.earth_chat.chatroom.controller;

import com.earth_chat.chatroom.controller.request.CreateChatroomRequest;
import com.earth_chat.chatroom.controller.request.UpdateChatroomRequest;
import com.earth_chat.chatroom.service.ChatroomService;
import com.earth_chat.common.custom.CustomUserDetails;
import com.earth_chat.common.enums.ChatroomSearchFiltering;
import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chatroom")
@RequiredArgsConstructor
@Tag(name = "채팅방 API Controller", description = "채팅방 CRUD API")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @Operation(summary = "채팅방 생성 API", description = "채팅방 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자를 찾지 못할 경우 발생")
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "채팅방 생성 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateChatroomRequest.class)
                    )
            )
            @RequestBody CreateChatroomRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseWrapperUtil.success("success", chatroomService.create(customUserDetails, request));
    }

    @Operation(summary = "채팅방 목록 조회 API", description = "채팅방 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> list(
            @Parameter(description = "페이지 번호", required = true)
            @RequestParam int page,
            @Parameter(description = "데이터 개수", required = true)
            @RequestParam int size,
            @Parameter(description = "채팅방명 또는 채팅방 설명", required = false)
            @RequestParam(required = false) String text,
            @Parameter(description = "필터링 조건", required = true)
            @RequestParam ChatroomSearchFiltering filter
    ) {
        return ResponseWrapperUtil.success("success", chatroomService.list(page, size, text, filter));
    }

    @Operation(summary = "채팅방 삭제 API", description = "채팅방 삭제 API ( 방장만 삭제 가능 )")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "방장 아닌 사용자가 삭제 요청을 했을 경우 발생"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 혹은 채팅방을 찾지 못할 경우 발생"),
    })
    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> delete(
            @Parameter(description = "채팅방 SEQ", required = true)
            @RequestParam Long chatroomSeq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseWrapperUtil.success("success", chatroomService.delete(customUserDetails, chatroomSeq));
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "채팅방 정보 수정 요청 객체",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateChatroomRequest.class)
                    )
            )
            @RequestBody UpdateChatroomRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseWrapperUtil.success("success", chatroomService.update(customUserDetails, request));
    }
}
