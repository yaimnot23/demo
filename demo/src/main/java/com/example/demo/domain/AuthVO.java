package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthVO {
    private int authNo;
    private String email;
    private String auth;
}
