package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.credential.UserCredential;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SimpleUploadCsvFileTest extends BaseClass {
    File file = new File("src/main/resources/upload.csv");

    @Description("This is simple automation script for upload csv file.")
    @DisplayName("Simple upload csv automation script.")
    @Test
    public void testSimpleResetQuestion() {
        new MainPage()
                .openListOfQuestionsPage()
                .enterCredentialUser(
                        new UserCredential(
                                "admin@qa.team",
                                "123456")
                ).uploadQuestionsFromFile(file)
                .verifyUploadQuestionsFile();
    }
}
