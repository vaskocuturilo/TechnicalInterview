package com.example.demo.service;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.TechnicalInterviewRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
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

        technicalInterviewEntities.stream().findAny().orElseThrow(() -> {
            QuestionNotFoundException questionNotFoundException = new QuestionNotFoundException(notFoundInfoMessage);
            log.debug("The list of questions were not found." + questionNotFoundException.getMessage());
            return questionNotFoundException;
        });

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
                    log.info("The questions were save");
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
                .orElseThrow(() -> {
                    QuestionNotFoundException questionNotFoundException = new QuestionNotFoundException(notFoundErrorMessage);
                    log.debug("The question with id = " + id + " not found." + questionNotFoundException.getMessage());
                    return questionNotFoundException;
                });

        technicalInterviewRepository.deleteById(id);
    }

    @Transactional
    public void deleteTechnicalInterviewQuestions() {
        List<TechnicalInterviewEntity> technicalInterviewEntities = technicalInterviewRepository.findAll();

        technicalInterviewEntities.stream().findAny().orElseThrow(() -> {
            QuestionNotFoundException questionNotFoundException = new QuestionNotFoundException(notFoundErrorMessage);
            log.debug("The questions were not found" + questionNotFoundException.getMessage());
            return questionNotFoundException;
        });

        technicalInterviewRepository.deleteAll();
    }

    @Transactional
    public void completeTechnicalInterviewTask(final boolean completed, final Long id) {
        Optional<TechnicalInterviewEntity> technicalInterviewEntity = technicalInterviewRepository.findById(id);

        technicalInterviewEntity
                .stream()
                .filter(question -> question.getTaskId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> {
                            QuestionNotFoundException questionNotFoundException = new QuestionNotFoundException(notFoundErrorMessage);
                            log.debug("The question with id = " + id + " not found." + questionNotFoundException.getMessage());
                            return questionNotFoundException;
                        });

        technicalInterviewRepository.setCompletedStatus(completed, id);
    }

    @Transactional
    public void resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewRepository.resetAllCompleted();
        log.debug("The method resetAllCompletedTechnicalInterviewTasks is done.");
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