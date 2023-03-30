package com.example.demo.pages;

import com.example.demo.credential.Gender;
import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

public class RegisterPage extends AbstractPages {
    private static By EMAIL = By.xpath("//input[@id='email']");

    private static By PASSWORD = By.xpath("//input[@id='password']");
    private static By GENDER = By.xpath("//select[@id='gender']");

    private static By SIGN_UP_BUTTON = By.xpath("//input[@value='Sign Up']");

    public SuccessRegisterPage addCredentialForRegister() {
        String credential = createEmail();
        WaitCondition waitCondition = new WaitCondition();
        waitCondition.waitForVisibilityOfElementLocatedBy(EMAIL).sendKeys(credential);
        waitCondition.waitForVisibilityOfElementLocatedBy(PASSWORD).sendKeys(credential);

        selectDropDown(GENDER).selectByValue(Gender.MALE.getGender());
        waitCondition.waitForVisibilityOfElementLocatedBy(SIGN_UP_BUTTON).click();

        return new SuccessRegisterPage();
    }
}
