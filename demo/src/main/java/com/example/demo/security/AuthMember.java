package com.example.demo.security;

import com.example.demo.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AuthMember extends User {
    private UserVO userVO;

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthMember(UserVO userVO) {
        super(userVO.getEmail(), userVO.getPwd(),
                userVO.getAuthList().stream()
                        .map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
                        .collect(Collectors.toList()));
        this.userVO = userVO;
    }
}
