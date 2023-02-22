package com.example.demo.repository;

import com.example.demo.entity.TechnicalInterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TechnicalInterviewRepository extends JpaRepository<TechnicalInterviewEntity, UUID> {
    @Modifying
    @Query("update TechnicalInterviewEntity tech set tech.completed = ?1 where tech.id = ?2")
    void setCompletedStatus(boolean completed, UUID id);

    @Query("SELECT tech FROM TechnicalInterviewEntity tech WHERE tech.completed = false ORDER BY function('RAND') LIMIT 1")
    List<TechnicalInterviewEntity> findQuestion();

    @Query("SELECT tech FROM TechnicalInterviewEntity  tech WHERE tech.taskName = ?1")
    TechnicalInterviewEntity findByTaskName(String taskName);

    @Query("SELECT COUNT(tech.completed) FROM TechnicalInterviewEntity tech WHERE tech.completed = true ")
    Integer findByQuestionIsPass();

    @Query("SELECT COUNT(tech.completed) FROM TechnicalInterviewEntity tech WHERE tech.completed = false")
    Integer findByQuestionIsFail();

    @Modifying
    @Query("update TechnicalInterviewEntity tech set tech.completed = false")
    void resetAllCompleted();
}
