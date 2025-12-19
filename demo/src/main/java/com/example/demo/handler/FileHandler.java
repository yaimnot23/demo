package com.example.demo.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.FileVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileHandler {

    // Define the upload path
    private final String UP_DIR = System.getProperty("user.dir") + "/src/main/resources/static/image";

    public List<FileVO> uploadFiles(MultipartFile[] files) {
        List<FileVO> fileList = new ArrayList<>();
        
        // Create the directory if it doesn't exist
        File dir = new File(UP_DIR);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        for(MultipartFile file : files) {
            if(file.isEmpty()) continue; // Skip empty files

            try {
                // Original file name
                String originalFileName = file.getOriginalFilename();
                // UUID for unique file name
                String uuid = UUID.randomUUID().toString();
                // Extension
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                // Saved file name (uuid + extension) -> keeping it simple logic or as requested? 
                // Plan said: fileName, uuid. user table has both.
                // Let's preserve original name in fileName and use uuid for PK/storage name prevention?
                // Actually typical logic: save as UUID_OriginalName to avoid collision? 
                // Or just UUID. User table has uuid, fileName. Let's save actual file as uuid + "_" + fileName to make it unique and recognizable.
                
                String savedFileName = uuid + "_" + originalFileName;
                
                // Full path
                File dest = new File(UP_DIR, savedFileName);
                
                // Save file
                file.transferTo(dest);

                // Check if image
                int fileType = isImageFile(file) ? 1 : 0;

                // Create FileVO object
                FileVO fvo = FileVO.builder()
                        .uuid(uuid)
                        .saveDir("/image") // Web access path
                        .fileName(originalFileName)
                        .fileType(fileType) // 1 for image, 0 for others
                        .fileSize(file.getSize())
                        .build();

                fileList.add(fvo);
            } catch (IOException e) {
                log.error(">>> File upload error: {}", e.getMessage());
            }
        }
        
        return fileList;
    }

    public void deleteFile(String uuid, String fileName) {
        File file = new File(UP_DIR, uuid + "_" + fileName);
        if(file.exists()) {
            file.delete();
        }
    }
    
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image");
    }
}
