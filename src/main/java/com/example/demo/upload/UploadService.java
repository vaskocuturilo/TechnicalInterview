package com.example.demo.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void upload(MultipartFile file);
}
