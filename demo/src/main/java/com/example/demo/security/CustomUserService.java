package com.example.demo.security;

import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO = userDAO.selectUser(username);
        if (userVO == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AuthMember(userVO);
    }
}
