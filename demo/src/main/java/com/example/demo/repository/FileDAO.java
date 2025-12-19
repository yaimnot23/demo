package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.FileVO;

@Mapper
public interface FileDAO {
    int insertFile(FileVO fvo);
    List<FileVO> getFileList(int bno);
    FileVO getFile(String uuid);
    int deleteFile(String uuid);
    void deleteAllFiles(int bno);
}
