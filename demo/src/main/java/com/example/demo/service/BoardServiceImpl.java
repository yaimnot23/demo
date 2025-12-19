package com.example.demo.service;


import com.example.demo.repository.BoardDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardDAO boardDAO;
    private final com.example.demo.repository.FileDAO fileDAO;

    @Override
    public void register(com.example.demo.domain.BoardVO boardVO) {
        boardDAO.insert(boardVO);
    }

    @Override
    public List<com.example.demo.domain.BoardVO> getList(com.example.demo.domain.PagingVO pgvo) {
        return boardDAO.getList(pgvo);
    }

    @Override
    public int getTotalCount(com.example.demo.domain.PagingVO pgvo) {
        return boardDAO.getTotalCount(pgvo);
    }

    @Override
    public com.example.demo.domain.BoardVO getDetail(int bno) {
        return boardDAO.getDetail(bno);
    }

    @Override
    public void modify(com.example.demo.domain.BoardVO boardVO) {
        boardDAO.modify(boardVO);
    }

    @Override
    public int remove(int bno) {
        fileDAO.deleteAllFiles(bno); // Remove files from DB before board
        return boardDAO.delete(bno);
    }

    @Override
    public int registerFile(com.example.demo.domain.FileVO fvo) {
        return fileDAO.insertFile(fvo);
    }

    @Override
    public List<com.example.demo.domain.FileVO> getFileList(int bno) {
        return fileDAO.getFileList(bno);
    }
    
    @Override
    public com.example.demo.domain.FileVO getFile(String uuid) {
        return fileDAO.getFile(uuid);
    }

    @Override
    public int deleteFile(String uuid) {
        return fileDAO.deleteFile(uuid);
    }
}
