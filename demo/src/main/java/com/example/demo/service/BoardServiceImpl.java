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

    @Override
    public void register(com.example.demo.domain.BoardVO boardVO) {
        boardDAO.insert(boardVO);
    }

    @Override
    public List<com.example.demo.domain.BoardVO> getList() {
        return boardDAO.getList();
    }

    @Override
    public com.example.demo.domain.BoardVO getDetail(int bno) {
        return boardDAO.getDetail(bno);
    }
}
