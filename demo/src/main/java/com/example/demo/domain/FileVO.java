package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileVO {
    private String uuid;
    private String saveDir;
    private String fileName;
    private int fileType;
    private int bno;
    private long fileSize;
    private String regDate;
}
