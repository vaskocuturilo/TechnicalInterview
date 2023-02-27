package com.example.demo.upload;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.NotCorrectlyCellException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class ParseHelper {
    @Value("${cell.info.message}")
    private String infoMessage;

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
        log.debug("Log Message -> : {} Start to parse Excel file.");
        Sheet sheet = null;
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            int sheets = workbook.getNumberOfSheets();
            for (int i = 0; i < sheets; i++) {
                sheet = workbook.getSheet(workbook.getSheetName(i));
            }
            Iterator<Row> rows = null;
            if (sheet != null) {
                rows = sheet.iterator();
            }

            List<TechnicalInterviewEntity> technicalInterviewEntities = new ArrayList<>();

            int rowNumber = 0;
            if (rows != null) {
                while (rows.hasNext()) {
                    Row currentRow = rows.next();
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity();

                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIdx) {
                            case 0 -> technicalInterview.setTitle(currentCell.getStringCellValue());
                            case 1 -> technicalInterview.setDescription(currentCell.getStringCellValue());
                            case 2 -> technicalInterview.setCompleted(currentCell.getBooleanCellValue());
                            default -> throw new NotCorrectlyCellException(infoMessage);
                        }
                        cellIdx++;
                    }

                    technicalInterviewEntities.add(technicalInterview);
                }
            }

            workbook.close();
            log.debug("Log Message -> : {} Pass to parse Excel file: ", technicalInterviewEntities);
            return technicalInterviewEntities;

        } catch (IOException exception) {
            log.debug("Log Message -> : {} fail to parse Excel file: ", exception.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + exception.getMessage());
        }
    }
}
