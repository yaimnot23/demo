package com.example.demo.service;

import com.example.demo.domain.ReplyVO;
import com.example.demo.repository.ReplyDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyServiceImpl implements CommentService {
    private final ReplyDAO replyDAO;

    @Override
    public int register(ReplyVO rvo) {
        return replyDAO.insert(rvo);
    }

    @Override
    public List<ReplyVO> getList(int bno, int page, int qty) {
        int pageStart = (page - 1) * qty;
        return replyDAO.getList(bno, pageStart, qty);
    }

    @Override
    public int getTotalCount(int bno) {
        return replyDAO.getTotalCount(bno);
    }

    @Override
    public int modify(ReplyVO rvo) {
        return replyDAO.update(rvo);
    }

    @Override
    public int remove(int rno) {
        return replyDAO.delete(rno);
    }
}
