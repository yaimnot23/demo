package com.example.demo.controller;

import com.example.demo.domain.UserVO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public void register() {
        log.info(">>> register page");
    }

    @PostMapping("/register")
    public String register(UserVO userVO) {
        log.info(">>> register: {}", userVO);
        int isOk = userService.register(userVO);
        return "index";
    }

    @GetMapping("/login")
    public void login() {
        log.info(">>> login page");
    }

    @GetMapping("/detail")
    public void detail(java.security.Principal principal, org.springframework.ui.Model model) {
        log.info(">>> detail page");
        String email = principal.getName();
        model.addAttribute("userVO", userService.getDetail(email));
    }

    @GetMapping("/list")
    public void list(org.springframework.ui.Model model) {
        log.info(">>> list page");
        model.addAttribute("list", userService.getUserList());
    }

    @GetMapping("/modify")
    public void modify(java.security.Principal principal, org.springframework.ui.Model model) {
        log.info(">>> modify page");
        String email = principal.getName();
        model.addAttribute("userVO", userService.getDetail(email));
    }

    @PostMapping("/modify")
    public String modify(UserVO userVO, 
                         @org.springframework.web.bind.annotation.RequestParam(name="file", required = false) org.springframework.web.multipart.MultipartFile file) {
        log.info(">>> modify: {}", userVO);
        
        if(file != null && !file.isEmpty()) {
            // FileHandler logic for single file? 
            // Reuse logic or create specialized one. FileHandler returns List<FileVO>.
            // But UserVO needs String profileImage (path/uuid_name).
            // Let's manually handle here or extend FileHandler.
            // Using existing FileHandler logic for simplicity but extracting logic helper.
            // Or just inline for now since it's one file.
            
           // Quick inline implementation reusing FileHandler knowledge
           // Actually, better to use FileHandler if possible, but it returns List<FileVO> and saves to DB table `board_file`.
           // Here we just want path in `member` table.
           // So, let's just use FileHandler's uploadFiles and take the first one?
           // But uploadFiles creates FileVO objects and returns them. It also saves to disk.
           // We can use it.
           java.util.List<com.example.demo.domain.FileVO> fileList = fileHandler.uploadFiles(new org.springframework.web.multipart.MultipartFile[]{file});
           if(fileList != null && fileList.size() > 0) {
               com.example.demo.domain.FileVO fvo = fileList.get(0);
               userVO.setProfileImage(fvo.getUuid() + "_" + fvo.getFileName());
           }
        }
        
        userService.modify(userVO);
        return "redirect:/user/detail";
    }
    
    @PostMapping("/remove")
    public String remove(java.security.Principal principal, jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) {
        String email = principal.getName();
        userService.remove(email);
        
        // Logout manually
        new org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler().logout(request, response, org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication());
        
        return "redirect:/";
    }

    private final com.example.demo.handler.FileHandler fileHandler;
    

}
