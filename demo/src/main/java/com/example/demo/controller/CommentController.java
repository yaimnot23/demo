package com.example.demo.controller;

import com.example.demo.domain.ReplyVO;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ReplyVO rvo) {
        log.info(">>> comment register: {}", rvo);
        int isOk = commentService.register(rvo);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/list/{bno}/{page}")
    public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno, @PathVariable("page") int page) {
        log.info(">>> comment list: bno={}, page={}", bno, page);
        int qty = 10; // Default qty per page
        List<ReplyVO> list = commentService.getList(bno, page, qty);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@PathVariable("rno") int rno, @RequestBody ReplyVO rvo) {
        log.info(">>> comment modify: rno={}, rvo={}", rno, rvo);
        rvo.setRno(rno);
        int isOk = commentService.modify(rvo);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") int rno) {
        log.info(">>> comment remove: rno={}", rno);
        int isOk = commentService.remove(rno);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
