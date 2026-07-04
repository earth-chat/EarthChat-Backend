package com.earth_chat.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {

    // 데이터
    private T content;

    // 데이터 개수
    private int size;

    // 페이지
    private int page;

    // 전체 데이터 개수
    private long totalSize;

    // 전체 페이지
    private long totalPage;
}
