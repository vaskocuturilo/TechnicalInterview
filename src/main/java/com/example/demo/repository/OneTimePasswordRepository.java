package com.example.demo.repository;

import com.example.demo.entity.OneTimePasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePasswordEntity, Long> {
}
