package com.example.demo;


import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.repository.TechnicalInterviewRepository;
import com.example.demo.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class SimpleUnitInterviewQuestionTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TechnicalInterviewRepository technicalInterviewRepository;

    @MockBean
    private StorageService storageService;

    @Test
    @Description("This is a simple unit test.")
    public void testCreateTechInterviewQuestionTask() {
        TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity();
        String text = createUniqTaskDescriptionName();
        technicalInterview.setTaskName(text);
        technicalInterview.setDescription(text);
        technicalInterview.setCompleted(true);
        TechnicalInterviewEntity technicalInterviewEntitySaved = technicalInterviewRepository.save(technicalInterview);

        TechnicalInterviewEntity technicalInterviewEntityExist = entityManager.find(TechnicalInterviewEntity.class, technicalInterviewEntitySaved.getId());

        assertThat(technicalInterview.getId()).isEqualTo(technicalInterviewEntityExist.getId());
        assertThat(technicalInterview.getTaskName()).isEqualTo(technicalInterviewEntityExist.getTaskName());
        assertThat(technicalInterview.getDescription()).isEqualTo(technicalInterviewEntityExist.getDescription());

    }

    @Test
    @Description("This is a simple unit test.")
    public void testVerifyTechInterviewQuestionIsNotEmpty() {
        List<TechnicalInterviewEntity> technicalInterviewRepositoryAll = technicalInterviewRepository.findAll();
        assertThat(technicalInterviewRepositoryAll).isNotEmpty();
    }

    @Test
    @Description("This is a simple unit test.")
    public void testFindTechnicalInterviewQuestionTask() {
        TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity();
        String text = createUniqTaskDescriptionName();

        technicalInterview.setTaskName(text);
        technicalInterview.setDescription(text);
        technicalInterview.setCompleted(true);
        technicalInterviewRepository.save(technicalInterview);


        TechnicalInterviewEntity technicalInterviewEntity = technicalInterviewRepository.findByTaskName(text);
        assertThat(technicalInterviewEntity.getTaskName()).isEqualTo(text);
    }

    @Test
    @Description("This is a simple unit test.")
    public void testVerifyTechnicalInterviewQuestionTaskSize() {
        TechnicalInterviewEntity technicalInterview = new TechnicalInterviewEntity();
        String text = createUniqTaskDescriptionName();

        technicalInterview.setTaskName(text);
        technicalInterview.setDescription(text);
        technicalInterview.setCompleted(true);
        technicalInterviewRepository.save(technicalInterview);

        List<TechnicalInterviewEntity> technicalInterviewRepositoryAll = technicalInterviewRepository.findAll();
        assertThat(technicalInterviewRepositoryAll.size()).isGreaterThan(1);

    }

    private String createUniqTaskDescriptionName() {
        return "task_name_is_" + new Date().getTime();
    }

}
