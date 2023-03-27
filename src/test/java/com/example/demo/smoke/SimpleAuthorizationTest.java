package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.credential.UserCredential;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleAuthorizationTest extends BaseClass {
    @Description("This is simple login automation script.")
    @DisplayName("Simple login automation script.")
    @Test
    public void simpleAuthorizationTest() {
        new MainPage()
                .openListOfQuestionsPage()
                .enterCredentialUser(
                        new UserCredential(
                                "admin@qa.team",
                                "123456")
                )
                .checkElementsLoanPage();
    }
}
