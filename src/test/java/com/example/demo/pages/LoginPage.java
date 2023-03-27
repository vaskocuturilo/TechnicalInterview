package com.example.demo.pages;

import com.example.demo.credential.UserCredential;
import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

public class LoginPage {
    private static By LOGIN_FORM_HEAD = By.xpath("//h2[@class='form-signin-heading']");
    private static By LOGIN_FORM = By.xpath("//form[@class='form-signin']");
    private static By USER_NAME = By.xpath("//input[@id='username']");
    private static By PASSWORD = By.xpath("//input[@id='password']");
    private static By SIGN_IN = By.xpath("//button[@type='submit']");

    public QuestionsPage enterCredentialUser(UserCredential userCredential) {
        checkThatLoginFormAvailable();
        enterUserEmail(userCredential);
        enterPassword(userCredential);
        clickSignInButton();

        return new QuestionsPage();
    }

    private LoginPage checkThatLoginFormAvailable() {
        WaitCondition waitCondition = new WaitCondition();

        waitCondition.waitForVisibilityOfElementLocatedBy(LOGIN_FORM).isDisplayed();
        waitCondition.waitForVisibilityOfElementLocatedBy(LOGIN_FORM_HEAD).isDisplayed();
        waitCondition.waitForVisibilityOfElementLocatedBy(USER_NAME).isDisplayed();
        waitCondition.waitForVisibilityOfElementLocatedBy(PASSWORD).isDisplayed();
        waitCondition.waitForVisibilityOfElementLocatedBy(SIGN_IN).isDisplayed();

        return this;
    }

    private LoginPage enterUserEmail(final UserCredential userCredential) {
        WaitCondition waitCondition = new WaitCondition();

        waitCondition.waitForVisibilityOfElementLocatedBy(USER_NAME).clear();
        waitCondition.waitForVisibilityOfElementLocatedBy(USER_NAME).sendKeys(userCredential.getUsername());

        return this;
    }

    private LoginPage enterPassword(final UserCredential userCredential) {
        WaitCondition waitCondition = new WaitCondition();

        waitCondition.waitForVisibilityOfElementLocatedBy(PASSWORD).clear();
        waitCondition.waitForVisibilityOfElementLocatedBy(PASSWORD).sendKeys(userCredential.getPassword());

        return this;
    }

    private LoginPage clickSignInButton() {
        WaitCondition waitCondition = new WaitCondition();

        waitCondition.waitForVisibilityOfElementLocatedBy(SIGN_IN).isDisplayed();
        waitCondition.waitForVisibilityOfElementLocatedBy(SIGN_IN).click();

        return this;
    }
}
