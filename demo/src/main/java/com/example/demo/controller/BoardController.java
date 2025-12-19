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

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
    private final BoardService boardService;
    private final com.example.demo.handler.FileHandler fileHandler;

    @GetMapping("/register")
    public void register(org.springframework.ui.Model model){
        model.addAttribute("boardVO", new BoardVO());
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO, @RequestParam(name="files", required = false) org.springframework.web.multipart.MultipartFile[] files) {
        log.info("register boardVO : {}", boardVO);
        List<com.example.demo.domain.FileVO> fileList = null;
        if(files != null && files[0].getSize() > 0) {
            fileList = fileHandler.uploadFiles(files);
        }
        
        boardService.register(boardVO);
        
        if(fileList != null) {
            for(com.example.demo.domain.FileVO fvo : fileList) {
                fvo.setBno(boardVO.getBno());
                boardService.registerFile(fvo);
            }
        }
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
    public String detail(Model model, @RequestParam("bno") int bno, java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request) {
        BoardVO boardVO = boardService.getDetail(bno);
        
        if(boardVO != null && Boolean.TRUE.equals(boardVO.getIsSecret())) {
            boolean isAuth = false;
            // 1. Check Login User (Writer or Admin)
            if(principal != null) {
                if(boardVO.getWriter().equals(principal.getName()) || request.isUserInRole("ROLE_ADMIN")) {
                    isAuth = true;
                }
            }
            if(!isAuth) {
                return "redirect:/board/secret?bno=" + bno;
            }
        }
        
        model.addAttribute("boardVO", boardVO);
        model.addAttribute("fileList", boardService.getFileList(bno));
        return "/board/detail";
    }

    @GetMapping("/secret")
    public void secret(@RequestParam("bno") int bno, Model model) {
        model.addAttribute("bno", bno);
    }

    @PostMapping("/secret")
    public String secret(@RequestParam("bno") int bno, @RequestParam("password") String password, Model model) {
        BoardVO boardVO = boardService.getDetail(bno);
        if(boardVO != null && boardVO.getPassword().equals(password)) {
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("fileList", boardService.getFileList(bno));
            return "/board/detail";
        }
        return "redirect:/board/secret?bno=" + bno + "&error=true";
    }

    @PostMapping("/modify")
    public String modify(BoardVO boardVO, 
                         @RequestParam(name="files", required = false) org.springframework.web.multipart.MultipartFile[] files,
                         @RequestParam(name="removeFiles", required = false) List<String> removeFiles,
                         java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request) {
        log.info("modify... boardVO : {}", boardVO);
        BoardVO original = boardService.getDetail(boardVO.getBno());
        
        if (principal != null && (original.getWriter().equals(principal.getName()) || request.isUserInRole("ROLE_ADMIN"))) {
            // 1. File Removal logic (Prioritize removal)
            if(removeFiles != null) {
                for(String uuid : removeFiles) {
                    com.example.demo.domain.FileVO fvo = boardService.getFile(uuid);
                    if(fvo != null) {
                        fileHandler.deleteFile(fvo.getUuid(), fvo.getFileName());
                        boardService.deleteFile(uuid);
                    }
                }
            }

            // 2. File Upload logic
            List<com.example.demo.domain.FileVO> fileList = null;
            if(files != null && files[0].getSize() > 0) {
                fileList = fileHandler.uploadFiles(files);
            }
            
            boardService.modify(boardVO);
            
            if(fileList != null) {
                for(com.example.demo.domain.FileVO fvo : fileList) {
                    fvo.setBno(boardVO.getBno());
                    boardService.registerFile(fvo);
                }
            }
        } else {
             log.warn("Unauthorized modify attempt by {}", principal != null ? principal.getName() : "anonymous");
        }
        return "redirect:/board/list"; // Should probably redirect to detail
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
