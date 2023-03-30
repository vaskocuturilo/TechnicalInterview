package com.example.demo.pages;

import com.example.demo.base.DriverHolder;
import com.example.demo.waiter.WaitCondition;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionsPage extends AbstractPages {
    private static By TITLE = By.xpath("//input[@id='title_name_input']");
    private static By DESCRIPTION = By.xpath("//input[@id='description_input']");
    private static By ADD_QUESTION_BUTTON = By.xpath("//input[@id='button_add_question']");
    private static By TABLE = By.xpath("//table[@id='main_table']");
    private static By TOTAL_COUNT = By.xpath("//h1[@id='total_count']");
    private static By PASS_COUNT = By.xpath("//h1[@id='pass_count']");
    private static By FAIL_COUNT = By.xpath("//h1[@id='fail_count']");
    private static By COMPLETE_CELL = By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(),'Test complete')]/following-sibling::td//button[@id='cell_complete']");
    private static By DELETE_BUTTON = By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(),'Question for delete')]/following-sibling::td//button[@id='cell_delete']");
    private static By DELETE_ALL_BUTTON = By.xpath("//input[@id='delete_button']");
    private static By TABLE_LIST = By.xpath("//table[@id='main_table']//tbody/tr");

    private static By UPLOAD_FILE = By.xpath("//input[@id='upload_file']");
    private static By UPLOAD_FILE_BUTTON = By.xpath("//input[@id='upload_questions']");

    private static By RESET_ALL_BUTTON = By.xpath("//input[@id='reset_button']");

    private static By MESSAGE = By.xpath("//div[@id='info_message']");

    private static final String UPLOAD_MESSAGE = "You successfully uploaded file is: %s";
    private static final String ERROR_UPLOAD_MESSAGE = "Maximum upload size of %s exceeded";

    public QuestionsPage checkElementsLoanPage() {
        WaitCondition waitCondition = new WaitCondition();

        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TITLE).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(DESCRIPTION).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(ADD_QUESTION_BUTTON).isDisplayed());
        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(TABLE).isDisplayed());

        return this;
    }

    public QuestionsPage addNewQuestion(final String name) {
        WaitCondition waitCondition = new WaitCondition();

        Integer totalCount = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText());
        Integer failCount = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText());

        waitCondition.waitForVisibilityOfElementLocatedBy(TITLE).sendKeys(name);
        waitCondition.waitForVisibilityOfElementLocatedBy(DESCRIPTION).sendKeys(name);
        waitCondition.waitForVisibilityOfElementLocatedBy(ADD_QUESTION_BUTTON).click();

        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText())).isEqualTo(++totalCount);
        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText())).isEqualTo(++failCount);

        return this;
    }

    public QuestionsPage tapCompleteQuestionButton() {
        WaitCondition waitCondition = new WaitCondition();
        Integer count = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(PASS_COUNT).getText());

        scrollDownPage();
        waiter(3000);
        waitCondition.waitForVisibilityOfElementLocatedBy(COMPLETE_CELL).click();
        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(PASS_COUNT).getText())).isEqualTo(++count);

        return this;
    }

    public QuestionsPage tapResetAllQuestionButton() {
        WaitCondition waitCondition = new WaitCondition();

        Integer count = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText());

        assertThat(convertListToLocator(TABLE_LIST).size()).isNotEqualTo(0);
        assertThat(count).isNotEqualTo(0);

        scrollDownPage();
        waiter(1000);
        waitCondition.waitForVisibilityOfElementLocatedBy(RESET_ALL_BUTTON).click();
        Alert alert = DriverHolder.getDriverThread().switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(FAIL_COUNT).getText())).isNotEqualTo(0);

        return this;
    }

    public QuestionsPage tapDeleteQuestionButton() {
        WaitCondition waitCondition = new WaitCondition();
        Integer count = Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText());

        scrollDownPage();
        waiter(3000);
        waitCondition.waitForVisibilityOfElementLocatedBy(DELETE_BUTTON).click();
        Alert alert = DriverHolder.getDriverThread().switchTo().alert();
        alert.accept();
        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText())).isEqualTo(--count);

        return this;
    }

    public QuestionsPage tapDeleteAllQuestionButton() {
        WaitCondition waitCondition = new WaitCondition();

        scrollDownPage();
        waiter(3000);

        waitCondition.waitForVisibilityOfElementLocatedBy(DELETE_ALL_BUTTON).click();
        Alert alert = DriverHolder.getDriverThread().switchTo().alert();
        alert.accept();
        assertThat(Integer.parseInt(waitCondition.waitForVisibilityOfElementLocatedBy(TOTAL_COUNT).getText())).isEqualTo(0);

        return this;
    }

    public QuestionsPage uploadQuestionsFromFile(File file) {
        WaitCondition waitCondition = new WaitCondition();

        waitCondition.waitForVisibilityOfElementLocatedBy(UPLOAD_FILE).sendKeys(file.getAbsolutePath());
        waitCondition.waitForVisibilityOfElementLocatedBy(UPLOAD_FILE_BUTTON).click();

        return this;
    }

    public QuestionsPage verifyUploadQuestionsFile() {
        WaitCondition waitCondition = new WaitCondition();

        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MESSAGE).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.json"));
        return this;
    }

    public QuestionsPage verifyFailedUploadQuestionsFile() {
        WaitCondition waitCondition = new WaitCondition();

        assertThat(waitCondition.waitForVisibilityOfElementLocatedBy(MESSAGE).getText()).isEqualTo(String.format(ERROR_UPLOAD_MESSAGE, "1MB"));
        return this;
    }
}
