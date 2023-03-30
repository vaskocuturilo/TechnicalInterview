package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.credential.UserCredential;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SimpleUploadJsonFileInCorrectSizeTest extends BaseClass {
    File file = new File("src/main/resources/10mb-sample.json");

    @Description("This is simple automation script for verify failed upload file with big size.")
    @DisplayName("Simple failed upload file automation script.")
    @Test
    public void testSimpleResetQuestion() {
        new MainPage()
                .openListOfQuestionsPage()
                .enterCredentialUser(
                        new UserCredential(
                                "admin@qa.team",
                                "123456")
                ).uploadQuestionsFromFile(file)
                .verifyFailedUploadQuestionsFile();
    }
}