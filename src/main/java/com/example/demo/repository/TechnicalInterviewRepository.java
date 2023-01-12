package com.example.demo.repository;

import com.example.demo.entity.TechnicalInterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TechnicalInterviewRepository extends JpaRepository<TechnicalInterviewEntity, Long> {

    @Modifying
    @Query("update TechnicalInterviewEntity tech set tech.completed = ?1 where tech.id = ?2")
    void setCompletedStatus(boolean completed, Long id);
}
