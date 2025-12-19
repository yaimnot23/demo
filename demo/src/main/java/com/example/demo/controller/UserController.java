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
    

}
