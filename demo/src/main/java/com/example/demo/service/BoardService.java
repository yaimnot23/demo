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
    
    // File related methods
    int registerFile(com.example.demo.domain.FileVO fvo);
    List<com.example.demo.domain.FileVO> getFileList(int bno);
    com.example.demo.domain.FileVO getFile(String uuid);
    int deleteFile(String uuid);
}
