package com.example.demo.repository;

import com.example.demo.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    UserVO selectUser(String username);
    int insertUser(UserVO userVO);
    int insertAuth(String email, String auth);
    int updateUser(UserVO userVO);
    int deleteUser(String email);
    java.util.List<UserVO> getUserList();
}
