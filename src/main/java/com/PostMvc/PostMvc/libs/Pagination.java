package com.PostMvc.PostMvc.libs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter

public class Pagination {
    private int page;
    private int pageSize;
    private int totalCount;
    private int totalPages;
    private int startPage;
    private int endPage;

    public Pagination(int page, int pageSize, int totalCount){
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

//        전체 페이지 수
        this.totalPages = (int)Math.ceil((double)totalCount / pageSize);

//        현재 페이지 기준 시작과 끝 페이지
        this.startPage = ((page - 1) / pageSize) * pageSize + 1;
        this.endPage = Math.min(startPage + (pageSize - 1), totalPages);
    }
    public int getOffset() { return (page - 1) * pageSize; }
}
