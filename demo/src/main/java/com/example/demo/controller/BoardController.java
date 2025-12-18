package com.example.demo.controller;


import com.example.demo.domain.BoardVO;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/register")
    public void register(org.springframework.ui.Model model){
        model.addAttribute("boardVO", new BoardVO());
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO) {
        log.info("register boardVO : {}", boardVO);
        boardService.register(boardVO);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(Model model) {
        log.info("list...");
        model.addAttribute("list", boardService.getList());
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam("bno") int bno) {
        BoardVO boardVO = boardService.getDetail(bno);
        model.addAttribute("boardVO", boardVO);
        return "board/detail";  
    }
    
}
