package com.example.demo.service;

import com.example.demo.domain.UserVO;

public interface UserService {
    int register(UserVO userVO);
    int loginLastDate(String email);
    void modify(UserVO userVO);
    void remove(String email);
    UserVO getDetail(String email);
    java.util.List<UserVO> getUserList();
}
