package com.example.demo.repository;

import com.example.demo.entity.TechnicalInterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TechnicalInterviewRepository extends JpaRepository<TechnicalInterviewEntity, Long> {
    @Modifying
    @Query("update TechnicalInterviewEntity tech set tech.completed = ?1 where tech.taskId = ?2")
    void setCompletedStatus(boolean completed, Long id);

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
