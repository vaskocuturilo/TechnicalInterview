package com.example.demo.service;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.repository.TechnicalInterviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class TechnicalInterviewService {

    @Autowired
    TechnicalInterviewRepository technicalInterviewRepository;


    public List<TechnicalInterviewEntity> getAllTechnicalInterviewTasks() {
        return technicalInterviewRepository.findAll();
    }

    public TechnicalInterviewEntity getRandomQuestion() {
      return technicalInterviewRepository.findQuestion();
    }

    public TechnicalInterviewEntity saveTechnicalInterviewTask(TechnicalInterviewEntity technicalInterviewEntity) {
        return technicalInterviewRepository.save(technicalInterviewEntity);
        1
    }

    public void deleteTechnicalInterviewTask(final Long id) {
        technicalInterviewRepository.deleteById(id);
    }

    public void completeTechnicalInterviewTask(final boolean completed, final Long id) {
        technicalInterviewRepository.setCompletedStatus(completed, id);
    }

    public TechnicalInterviewEntity uploadTechnicalInterviewTasks(String technicalInterviewTasks) throws IOException {
        TechnicalInterviewEntity technicalInterviewEntity = new TechnicalInterviewEntity();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            technicalInterviewEntity = objectMapper.readValue(technicalInterviewTasks, TechnicalInterviewEntity.class);
        } catch (IOException exception) {
            throw new IOException("Error upload file" + exception.getMessage());
        }
        return technicalInterviewEntity;
    }
}