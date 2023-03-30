package com.example.demo.pages;

import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

public class MainPage {

    private static By MENU = By.xpath("//div[@id='menuToggle']");
    private static By ABOUT_ITEM = By.xpath("//a[@id='link_about']");
    private static By LIST_QUESTIONS_BUTTON = By.xpath("//input[@id='list_of_questions']");
    private static By LOGIN_BUTTON = By.xpath("//input[@id='login_page']");

    private static By SIGN_UP = By.xpath("//input[@id='register_page']");

    public LoginPage openListOfQuestionsPage() {
        WaitCondition waitCondition = new WaitCondition();
        waitCondition.waitForVisibilityOfElementLocatedBy(LIST_QUESTIONS_BUTTON).click();

        return new LoginPage();
    }

    public LoginPage openLoginPage() {
        WaitCondition waitCondition = new WaitCondition();
        waitCondition.waitForVisibilityOfElementLocatedBy(LOGIN_BUTTON).click();

        return new LoginPage();
    }

    public RegisterPage openRegisterPage() {
        WaitCondition waitCondition = new WaitCondition();
        waitCondition.waitForVisibilityOfElementLocatedBy(SIGN_UP).click();

        return new RegisterPage();
    }

    public HelpPage openHelpPage() {
        return new HelpPage();
    }

    public SubPageModalWindow openMenuMainPage() {
        WaitCondition waitCondition = new WaitCondition();
        waitCondition.waitForVisibilityOfElementLocatedBy(MENU).click();
        waitCondition.waitForVisibilityOfElementLocatedBy(ABOUT_ITEM).click();

        return new SubPageModalWindow();
    }
}
