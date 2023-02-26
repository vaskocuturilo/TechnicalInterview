package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j2
public class UploadHelper {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<TechnicalInterviewEntity> uploadEntities(final MultipartFile file, final InputStream inputStream) throws IOException {
        List<TechnicalInterviewEntity> technicalInterviewEntities;
        TypeReference<List<TechnicalInterviewEntity>> typeReference = new TypeReference<List<TechnicalInterviewEntity>>() {
        };
        switch (file.getContentType()) {
            case "text/csv" -> {
                technicalInterviewEntities = new ParseHelper().parseCsvFile(inputStream);
                log.debug("Log Message -> The text/csv file was parsing : {}", technicalInterviewEntities.toArray());
            }
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> {
                technicalInterviewEntities = new ParseHelper().parseExcelFile(inputStream);
                log.debug("Log Message -> The excel file was parsing : {}", technicalInterviewEntities.toArray());
            }
            case "application/json" -> {
                technicalInterviewEntities = mapper.readValue(inputStream, typeReference);
                log.debug("Log Message -> The json file was parsing", technicalInterviewEntities.toArray());
            }
            default ->
                    throw new IllegalStateException("unknown format of content type for file " + file.getContentType());
        }
        log.debug("Log Message -> : {} The  technicalInterviewEntities log", technicalInterviewEntities.toArray());
        return technicalInterviewEntities;
    }
}
