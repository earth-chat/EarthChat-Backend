package com.earth_chat.commoncode.service;

import com.earth_chat.commoncode.controller.response.CommonCodeDetailResponse;
import com.earth_chat.commoncode.controller.response.GroupCodeResponse;

import java.util.List;

public interface CommonCodeService {

    /**
     * 그룹 코드 목록 조회.
     * @return List<GroupCodeResponse>
     */
    List<GroupCodeResponse> selectCommonGroupCodes();

    /**
     * 그룹 코드 상세 목록 조회.
     * @param groupCode 그룹 코드
     * @return List<CommonCodeDetailResponse>
     */
    List<CommonCodeDetailResponse> selectCommonCodeDetails(String groupCode);
}
