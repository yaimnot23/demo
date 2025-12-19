package com.example.demo.controller;


import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
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
    public void list(Model model, PagingVO pgvo) {
        log.info("list... pgvo : {}", pgvo);
        
        // PagingHandler calculation
        int totalCount = boardService.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        
        model.addAttribute("list", boardService.getList(pgvo));
        model.addAttribute("ph", ph);
    }

    @GetMapping("/detail")
    public void detail(Model model, @RequestParam("bno") int bno) {
        model.addAttribute("boardVO", boardService.getDetail(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO boardVO, java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request) {
        log.info("modify... boardVO : {}", boardVO);
        BoardVO original = boardService.getDetail(boardVO.getBno());
        
        if (principal != null && (original.getWriter().equals(principal.getName()) || request.isUserInRole("ROLE_ADMIN"))) {
            boardService.modify(boardVO);
        } else {
             log.warn("Unauthorized modify attempt by {}", principal != null ? principal.getName() : "anonymous");
        }
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") int bno, java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request) {
        log.info("remove... bno : {}", bno);
        BoardVO original = boardService.getDetail(bno);
        
        if (principal != null && (original.getWriter().equals(principal.getName()) || request.isUserInRole("ROLE_ADMIN"))) {
            boardService.remove(bno);
        } else {
            log.warn("Unauthorized remove attempt by {}", principal != null ? principal.getName() : "anonymous");
        }
        return "redirect:/board/list";
    }
    
}
