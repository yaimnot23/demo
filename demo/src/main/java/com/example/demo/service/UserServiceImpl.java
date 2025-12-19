package com.example.demo.service;

import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int register(UserVO userVO) {
        // 비밀번호 암호화
        userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
        // 회원 가입
        int isUserOk = userDAO.insertUser(userVO);
        
        // 회원정보가 정상적으로 들어갔을 때만 권한 부여 (ROLE_USER)
        if(isUserOk > 0) {
            return userDAO.insertAuth(userVO.getEmail(), "ROLE_USER");
        }
        return 0; // 실패 시 0 반환
    }

    @Override
    public int loginLastDate(String email) {
        // 마지막 로그인 시간 업데이트 로직 mapper 에 구현 아직 안함
        return 0; // 구현 예정
    }
}
