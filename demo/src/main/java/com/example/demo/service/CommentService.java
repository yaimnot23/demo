package com.example.demo.service;

import com.example.demo.domain.ReplyVO;
import java.util.List;

public interface CommentService {
    int register(ReplyVO rvo);

    List<ReplyVO> getList(int bno, int page, int qty);

    int getTotalCount(int bno);

    int modify(ReplyVO rvo);

    int remove(int rno);
}
