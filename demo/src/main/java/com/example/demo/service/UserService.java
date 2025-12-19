package com.example.demo.service;

import com.example.demo.domain.UserVO;

public interface UserService {
    int register(UserVO userVO);
    int loginLastDate(String email);
}
