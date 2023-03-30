package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.credential.UserCredential;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleDeleteQuestionTest extends BaseClass {

    final String title = "Question for delete";

    @Description("This is simple automation script for delete a new question.")
    @DisplayName("Simple delete a new question automation script.")
    @Test
    public void testSimpleDeleteQuestion() {
        new MainPage()
                .openListOfQuestionsPage()
                .enterCredentialUser(
                        new UserCredential(
                                "admin@qa.team",
                                "123456")
                ).addNewQuestion(title)
                .tapDeleteQuestionButton();
    }
}
