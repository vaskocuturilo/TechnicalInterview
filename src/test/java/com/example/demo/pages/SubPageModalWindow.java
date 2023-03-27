package com.example.demo.pages;

import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;


public class SubPageModalWindow {
    private static By MODAL_WINDOW = By.xpath("//div[@class='modal']");
    private static By MODAL_CLOSE = By.xpath("//button[@class='close-modal']");
    private static By MODAL_TITLE = By.xpath("//div[@class='modal']/h1");
    private static By MODAL_TEXT = By.xpath("//div[@class='modal']/p");
    private static String MODAL_TITLE_TEXT = "I'm a modal window";
    private static String MODAL_VERIFY_TEXT = "This is a modal window for a technical interview application.";

    public SubPageModalWindow checkModalWindow() {
        WaitCondition waitCondition = new WaitCondition();
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MODAL_WINDOW).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MODAL_CLOSE).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MODAL_TITLE).getText().contains(MODAL_TITLE_TEXT));
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MODAL_TEXT).getText().contains(MODAL_VERIFY_TEXT));
        return this;
    }
}
