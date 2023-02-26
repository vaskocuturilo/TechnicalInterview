package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.service.TechnicalInterviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
public class FileSystemUploadService implements UploadService {
    @Value("${app.upload.path}")
    private String location;
    final TechnicalInterviewService technicalInterviewService;

    public FileSystemUploadService(TechnicalInterviewService technicalInterviewService) {
        this.technicalInterviewService = technicalInterviewService;
    }

    @Override
    public void upload(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(location + file.getOriginalFilename());
        } catch (FileNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : {}", e.getMessage());
            }
            throw new RuntimeException(e);
        }
        try {
            List<TechnicalInterviewEntity> technicalInterviewEntities = new UploadHelper().uploadEntities(file, inputStream);
            technicalInterviewService.saveTechnicalInterviewTasks(technicalInterviewEntities);
            log.info(file.getOriginalFilename() + " was save to upload-dir ");
        } catch (IOException e) {
            log.debug("Log Message -> : {}", e.getMessage());
        }
    }
}
