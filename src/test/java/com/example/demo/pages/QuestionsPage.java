package com.example.demo.pages;

import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionsPage {

    private static By TITLE = By.xpath("//input[@id='title_name_input']");
    private static By DESCRIPTION = By.xpath("//input[@id='description_input']");
    private static By ADD_QUESTION_BUTTON = By.xpath("//input[@id='button_add_question']");
    private static By TABLE = By.xpath("//table[@id='main_table']");
    private static By TOTAL_COUNT = By.xpath("//h1[@id='total_count']");
    private static By PASS_COUNT = By.xpath("//h1[@id='pass_count']");
    private static By FAIL_COUNT = By.xpath("//h1[@id='fail_count']");

    public QuestionsPage checkElementsLoanPage() {
        WaitCondition waitCondition = new WaitCondition();

        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TITLE).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(DESCRIPTION).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(ADD_QUESTION_BUTTON).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TABLE).isDisplayed());
        return this;
    }

    public QuestionsPage addNewQuestion() {
        WaitCondition waitCondition = new WaitCondition();

        Integer totalCount = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText());
        Integer failCount = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText());

        waitCondition.waitForVisibilityOfElementLocatedBy(TITLE).sendKeys("Test1");
        waitCondition.waitForVisibilityOfElementLocatedBy(DESCRIPTION).sendKeys("Test1");
        waitCondition.waitForVisibilityOfElementLocatedBy(ADD_QUESTION_BUTTON).click();

        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText())).isEqualTo(++totalCount);
        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText())).isEqualTo(++failCount);

        return this;
    }
}
