package com.example.demo.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyVO {
    private int rno;
    private int bno;
    private String reply;
    private String replyer;
    private LocalDateTime replyDate;
    private LocalDateTime updateDate;
}
