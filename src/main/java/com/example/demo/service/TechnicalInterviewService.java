package com.example.demo.service;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.TechnicalInterviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalInterviewService {

    final TechnicalInterviewRepository technicalInterviewRepository;

    @Value("${question.error.message}")
    private String notFoundErrorMessage;

    @Value("${question.info.message}")
    private String notFoundInfoMessage;

    public TechnicalInterviewService(TechnicalInterviewRepository technicalInterviewRepository) {
        this.technicalInterviewRepository = technicalInterviewRepository;
    }

    @Transactional
    public List<TechnicalInterviewEntity> getAllTechnicalInterviewTasks() {
        return technicalInterviewRepository.findAll();
    }

    @Transactional
    public List<TechnicalInterviewEntity> getRandomQuestion() {
        List<TechnicalInterviewEntity> technicalInterviewEntities = technicalInterviewRepository.findQuestion();

        technicalInterviewEntities.stream().findAny().orElseThrow(() -> new QuestionNotFoundException(notFoundInfoMessage));

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
        Optional<TechnicalInterviewEntity> technicalInterviewEntity = technicalInterviewRepository.findById(id);

        technicalInterviewEntity
                .stream()
                .filter(question -> question.getTaskId().equals(id))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException(notFoundErrorMessage));

        technicalInterviewRepository.deleteById(id);
    }

    @Transactional
    public void deleteTechnicalInterviewQuestions() {
        List<TechnicalInterviewEntity> technicalInterviewEntities = technicalInterviewRepository.findAll();

        technicalInterviewEntities.stream().findAny().orElseThrow(() -> new QuestionNotFoundException(notFoundErrorMessage));

        technicalInterviewRepository.deleteAll();
    }

    @Transactional
    public void completeTechnicalInterviewTask(final boolean completed, final Long id) {
        Optional<TechnicalInterviewEntity> technicalInterviewEntity = technicalInterviewRepository.findById(id);

        technicalInterviewEntity
                .stream()
                .filter(question -> question.getTaskId().equals(id))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException(notFoundErrorMessage));

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