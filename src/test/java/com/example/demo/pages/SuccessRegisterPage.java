package com.example.demo.pages;

import com.example.demo.base.DriverHolder;
import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuccessRegisterPage {

    private static By ONE_TIME_PASSWORD_TEXT = By.xpath("//div/p");

    private static By ONE_TIME_PASSWORD_CODE = By.xpath("//input[@id='one_time_password']");

    private static By VERIFY_ONE_TIME_PASSWORD_BUTTON = By.xpath("//input[@id='verify_one_time_password']");

    public SuccessRegisterPage addOneTimePasswordCode() {
        WaitCondition waitCondition = new WaitCondition();
        String code = waitCondition.waitForVisibilityOfElementLocatedBy(ONE_TIME_PASSWORD_TEXT).getText();
        waitCondition.waitForVisibilityOfElementLocatedBy(ONE_TIME_PASSWORD_CODE).sendKeys(code);
        waitCondition.waitForVisibilityOfElementLocatedBy(VERIFY_ONE_TIME_PASSWORD_BUTTON).click();

        assertTrue(DriverHolder.getDriverThread().getCurrentUrl().contains("http://localhost:8080/login"));

        return new SuccessRegisterPage();
    }
}
