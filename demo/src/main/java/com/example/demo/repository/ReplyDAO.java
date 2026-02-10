package com.example.demo.repository;

import com.example.demo.domain.ReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyDAO {
    int insert(ReplyVO rvo);

    List<ReplyVO> getList(@Param("bno") int bno, @Param("pageStart") int pageStart, @Param("qty") int qty);

    int getTotalCount(int bno);

    int update(ReplyVO rvo);

    int delete(int rno);
}
