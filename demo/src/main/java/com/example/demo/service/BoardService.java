package com.example.demo.service;

import java.util.List;

public interface BoardService {
    void register(com.example.demo.domain.BoardVO boardVO);
    List<com.example.demo.domain.BoardVO> getList();
    com.example.demo.domain.BoardVO getDetail(int bno);
}
