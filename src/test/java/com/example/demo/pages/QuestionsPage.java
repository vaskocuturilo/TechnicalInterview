package com.example.demo.pages;

import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionsPage {

    private static By TITLE = By.xpath("//input[@id='title_name_input']");
    private static By DESCRIPTION = By.xpath("//input[@id='description_input']");
    private static By ADD_QUESTION_BUTTON = By.xpath("//input[@id='button_add_question']");
    private static By TABLE = By.xpath("//table[@id='main_table']");

    public QuestionsPage checkElementsLoanPage() {
        WaitCondition waitCondition = new WaitCondition();

        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TITLE).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(DESCRIPTION).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(ADD_QUESTION_BUTTON).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TABLE).isDisplayed());
        return this;
    }
}
