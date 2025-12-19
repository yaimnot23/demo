package com.example.demo.handler;

import com.example.demo.domain.PagingVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {
    private int startPage;
    private int endPage;
    private boolean prev, next;
    
    private int totalCount;
    private PagingVO pgvo;

    public PagingHandler(PagingVO pgvo, int totalCount) {
        this.pgvo = pgvo;
        this.totalCount = totalCount;

        this.endPage = (int) (Math.ceil(pgvo.getPageNo() / 10.0)) * 10;
        this.startPage = endPage - 9;

        int realEndPage = (int) (Math.ceil((totalCount * 1.0) / pgvo.getQty()));

        if (realEndPage < this.endPage) {
            this.endPage = realEndPage;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEndPage;
    }
}
