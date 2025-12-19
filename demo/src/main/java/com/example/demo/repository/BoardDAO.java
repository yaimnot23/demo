package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDAO {
    void insert(com.example.demo.domain.BoardVO boardVO);
    List<com.example.demo.domain.BoardVO> getList(com.example.demo.domain.PagingVO pgvo);
    com.example.demo.domain.BoardVO getDetail(int bno);
    int getTotalCount(com.example.demo.domain.PagingVO pgvo);
    void modify(com.example.demo.domain.BoardVO boardVO);
    int delete(int bno);
}
