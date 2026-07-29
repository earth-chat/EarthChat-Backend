package com.earth_chat.commoncode.controller;

import com.earth_chat.common.util.ResponseWrapper;
import com.earth_chat.common.util.ResponseWrapperUtil;
import com.earth_chat.commoncode.service.CommonCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/common-code")
@RequiredArgsConstructor
@Tag(name = "공통 코드 API Controller", description = "공통 코드 관련 API")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @Operation(summary = "그룹 코드 목록 조회 API", description = "그룹 코드 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping("/group")
    public ResponseEntity<ResponseWrapper> groupList() {
        return ResponseWrapperUtil.success("success", commonCodeService.selectCommonGroupCodes());
    }

    @Operation(summary = "그룹 코드 상세 목록 조회 API", description = "그룹 코드 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping
    public ResponseEntity<ResponseWrapper> detailList(
            @Parameter(description = "그룹 코드", required = true)
            @RequestParam String groupCode
    ) {
        return ResponseWrapperUtil.success("success", commonCodeService.selectCommonCodeDetails(groupCode));
    }
}
