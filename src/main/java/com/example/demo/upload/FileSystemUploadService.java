package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.StorageException;
import com.example.demo.service.TechnicalInterviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
public class FileSystemUploadService implements UploadService {
    @Autowired
    TechnicalInterviewService technicalInterviewService;

    @Override
    public void upload(final String file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to upload empty file.");
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<TechnicalInterviewEntity>> typeReference = new TypeReference<List<TechnicalInterviewEntity>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(file);
        try {
            List<TechnicalInterviewEntity> technicalInterviewEntities = mapper.readValue(inputStream, typeReference);
            technicalInterviewService.saveTechnicalInterviewTasks(technicalInterviewEntities);
            log.info("technicalInterviewEntities was save to upload-dir", technicalInterviewEntities);
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("Log Message -> : Class FileSystemUploadService, method upload is down.", e.getMessage());
            }
        }
    }
}
