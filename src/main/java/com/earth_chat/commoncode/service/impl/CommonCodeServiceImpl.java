package com.earth_chat.commoncode.service.impl;

import com.earth_chat.commoncode.controller.response.CommonCodeDetailResponse;
import com.earth_chat.commoncode.controller.response.GroupCodeResponse;
import com.earth_chat.commoncode.mapper.CommonCodeMapper;
import com.earth_chat.commoncode.service.CommonCodeService;
import com.earth_chat.commoncode.vo.CommonCodeDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeMapper commonCodeMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupCodeResponse> selectCommonGroupCodes() {
        List<GroupCodeResponse> result = commonCodeMapper.selectGroupCodes().stream()
                .map(GroupCodeResponse::of)
                .toList();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommonCodeDetailResponse> selectCommonCodeDetails(String groupCode) {
        List<CommonCodeDetailVo> voList = commonCodeMapper.selectCodeDetailByGroupCode(groupCode);

        List<CommonCodeDetailResponse> result = voList.stream()
                .map(CommonCodeDetailResponse::of)
                .toList();

        return result;
    }
}
