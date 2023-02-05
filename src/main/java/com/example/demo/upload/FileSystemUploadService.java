package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.service.TechnicalInterviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<TechnicalInterviewEntity>> typeReference = new TypeReference<List<TechnicalInterviewEntity>>() {
        };
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(location + file.getOriginalFilename());
        } catch (FileNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : Class FileSystemUploadService, method upload. File not found.", e.getMessage());
            }
            throw new RuntimeException(e);
        }
        try {
            List<TechnicalInterviewEntity> technicalInterviewEntities = mapper.readValue(inputStream, typeReference);
            technicalInterviewService.saveTechnicalInterviewTasks(technicalInterviewEntities);
            log.info(file.getOriginalFilename() + " was save to upload-dir ");
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : Class FileSystemUploadService, method upload is down.", e.getMessage());
            }
        }
    }
}
