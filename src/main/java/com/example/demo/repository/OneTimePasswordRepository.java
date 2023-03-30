package com.example.demo.repository;

import com.example.demo.entity.OneTimePasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePasswordEntity, Long> {
    @Query("SELECT code.oneTimePasswordCode FROM OneTimePasswordEntity code WHERE code.user.id = ?1")
    Integer findByOneTimePasswordCode(final Long id);

    @Modifying
    @Query("delete from OneTimePasswordEntity code where code.oneTimePasswordCode = :code")
    void deleteByOneTimePasswordCode(final Integer code);
}
