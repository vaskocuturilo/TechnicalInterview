package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.credential.UserCredential;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleCompleteQuestionTest extends BaseClass {

    final String title = "Test complete";

    @Description("This is simple automation script for complete a new question.")
    @DisplayName("Simple complete a new question automation script.")
    @Test
    public void testSimpleAddNewQuestion() {
        new MainPage()
                .openListOfQuestionsPage()
                .enterCredentialUser(
                        new UserCredential(
                                "admin@qa.team",
                                "123456")
                ).addNewQuestion(title)
                .tapCompleteQuestionButton();
    }
}
