package com.example.demo.service;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;

import java.util.List;

public interface BoardService {
    void register(BoardVO boardVO);
    List<BoardVO> getList(PagingVO pgvo);
    BoardVO getDetail(int bno);
    int getTotalCount(PagingVO pgvo);
    void modify(BoardVO boardVO);
    int remove(int bno);
}
