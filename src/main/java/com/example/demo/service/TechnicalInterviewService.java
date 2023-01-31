package com.example.demo.service;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.repository.TechnicalInterviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class TechnicalInterviewService {

    @Autowired
    TechnicalInterviewRepository technicalInterviewRepository;


    @Transactional
    public List<TechnicalInterviewEntity> getAllTechnicalInterviewTasks() {
        return technicalInterviewRepository.findAll();
    }

    @Transactional
    public TechnicalInterviewEntity getRandomQuestion() {
        return technicalInterviewRepository.findQuestion();
    }

    @Transactional
    public TechnicalInterviewEntity saveTechnicalInterviewTask(TechnicalInterviewEntity technicalInterviewEntity) {
        return technicalInterviewRepository.save(technicalInterviewEntity);
    }

    @Transactional
    public void deleteTechnicalInterviewTask(final Long id) {
        technicalInterviewRepository.deleteById(id);
    }

    @Transactional
    public void completeTechnicalInterviewTask(final boolean completed, final Long id) {
        technicalInterviewRepository.setCompletedStatus(completed, id);
    }

    @Transactional
    public void resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewRepository.resetAllCompleted();
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