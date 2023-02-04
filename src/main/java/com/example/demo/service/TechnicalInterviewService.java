package com.example.demo.service;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.repository.TechnicalInterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<TechnicalInterviewEntity> getRandomQuestion() {
        return technicalInterviewRepository.findQuestion();
    }

    @Transactional
    public TechnicalInterviewEntity saveTechnicalInterviewTask(TechnicalInterviewEntity technicalInterviewEntity) {

        return technicalInterviewRepository.save(technicalInterviewEntity);
    }

    @Transactional
    public void editTechnicalInterviewTask(TechnicalInterviewEntity technicalInterviewEntity, final Long id) {
        technicalInterviewRepository.findById(id)
                .ifPresent(question -> {
                    question.setTaskName(technicalInterviewEntity.getTaskName());
                    question.setDescription(technicalInterviewEntity.getDescription());

                    technicalInterviewRepository.save(question);
                });
    }

    @Transactional
    public Iterable<TechnicalInterviewEntity> saveTechnicalInterviewTasks(List<TechnicalInterviewEntity> technicalInterviewEntities) {
        return technicalInterviewRepository.saveAll(technicalInterviewEntities);
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

    @Transactional
    public Integer getAllTechnicalInterviewQuestionStatusPass() {
        return technicalInterviewRepository.findByQuestionIsPass();
    }

    @Transactional
    public Integer getAllTechnicalInterviewQuestionStatusFail() {
        return technicalInterviewRepository.findByQuestionIsFail();
    }
}