package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
    private int bno;
    private String title;
    private String content;
    private String writer;
    private int readCount;
    private String isDel;
    private Date regDate;
    private int likeCount;
    private Boolean isSecret;
    private String password;
}
