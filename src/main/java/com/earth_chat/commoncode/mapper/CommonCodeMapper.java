package com.earth_chat.commoncode.mapper;

import com.earth_chat.commoncode.vo.CommonCodeDetailVo;
import com.earth_chat.commoncode.vo.CommonCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {

    /**
     * 그룹 코드 목록 조회.
     * @return List<CommonCodeVo>
     */
    List<CommonCodeVo> selectGroupCodes();

    /**
     * 그룹 코드 상세 목록 조회.
     * @param groupCode 그룹 코드
     * @return List<CommonCodeDetailVo>
     */
    List<CommonCodeDetailVo> selectCodeDetailByGroupCode(String groupCode);
}
