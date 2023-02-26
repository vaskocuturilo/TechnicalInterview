package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ParseHelper {
    public List<TechnicalInterviewEntity> parseCsvFile(InputStream inputStream) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TechnicalInterviewEntity> technicalInterviewEntities = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity(
                        csvRecord.get("title"),
                        csvRecord.get("description"),
                        Boolean.parseBoolean(csvRecord.get("completed")));

                technicalInterviewEntities.add(technicalInterview);
            }
            log.info(technicalInterviewEntities);
            return technicalInterviewEntities;
        }
    }

    public List<TechnicalInterviewEntity> parseExcelFile(InputStream inputStream) {
        return null;
    }
}
