package com.example.demo;


import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.repository.TechnicalInterviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SimpleUnitCreateTechInterviewQuestionTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TechnicalInterviewRepository technicalInterviewRepository;


    @Test()
    @Description("This is a simple unit test.")
    public void testSimpleUnitCreateTechInterviewQuestion() {
        TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity();

        technicalInterview.setTaskName("Test task name");
        technicalInterview.setDescription("Description for test task name");
        technicalInterview.setCompleted(true);
        TechnicalInterviewEntity technicalInterviewEntitySaved = technicalInterviewRepository.save(technicalInterview);

        TechnicalInterviewEntity technicalInterviewEntityExist = entityManager.find(TechnicalInterviewEntity.class, technicalInterviewEntitySaved.getTaskId());

        assertThat(technicalInterview.getTaskId()).isEqualTo(technicalInterviewEntityExist.getTaskId());
        assertThat(technicalInterview.getTaskName()).isEqualTo(technicalInterviewEntityExist.getTaskName());
        assertThat(technicalInterview.getDescription()).isEqualTo(technicalInterviewEntityExist.getDescription());

    }
}
