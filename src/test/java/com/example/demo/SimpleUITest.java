package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUITest {
    private static WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private static final int DELAY = 10;
    private static By TASK_NAME = By.xpath("//input[@name='taskName']");
    private static By DESCRIPTION = By.xpath("//input[@name='description']");
    private static By ADD_QUESTION_BUTTON = By.xpath("//input[@id='button_add_question']");
    private static By DELETE_LINK = By.xpath("//a[@id='cell_delete']");
    private static By DELETE_BUTTON = By.xpath("//input[@id='delete_button']");
    private static By TABLE = By.xpath("//table[@id='main_table']");
    private static By MAIN_PAGE_LINK_QUESTIONS = By.xpath("//input[@id='list_of_questions']");
    private static By USER_NAME = By.xpath("//input[@id='username']");
    private static By PASSWORD = By.xpath("//input[@id='password']");
    private static By SIGN_IN = By.xpath("//button[@type='submit']");
    private static By TOTAL_COUNT = By.xpath("//h1[@id='total_count']");
    private static By PASS_COUNT = By.xpath("//h1[@id='pass_count']");
    private static By FAIL_COUNT = By.xpath("//h1[@id='fail_count']");
    private static By UPLOAD_FILE = By.xpath("//input[@id='upload_file']");
    private static By UPLOAD_FILE_BUTTON = By.xpath("//input[@id='upload_questions']");
    private static By COMPLETE_CELL = By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(),'Test complete')]/following-sibling::td//a[@id='cell_complete']");
    private static By TABLE_LIST = By.xpath("//table[@id='main_table']//tbody/tr");
    private static By MESSAGE = By.xpath("//p[@id='info_message']");
    private static final String UPLOAD_MESSAGE = "You successfully uploaded file is: %s";
    private static final String ERROR_UPLOAD_MESSAGE = "Maximum upload size of %s exceeded";

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpDriver() {
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriver.get("http://localhost:8080/");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_PAGE_LINK_QUESTIONS)).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(USER_NAME)).sendKeys("user");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD)).sendKeys("user");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SIGN_IN)).click();
    }

    @Test
    public void testVerifyTitle() {
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");
    }

    @Test
    public void testVerifyAddElementsOnPage() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        Integer totalCount = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText());

        Integer failCount = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(++totalCount);
        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText())).isEqualTo(++failCount);
    }

    @Test
    public void testVerifyCompleteQuestion() {
        final String taskName = "Test complete";
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        Integer count = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASS_COUNT)).getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys(taskName);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys(taskName);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        scrollPage();
        waiter(3000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_CELL)).click();
        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASS_COUNT)).getText())).isEqualTo(++count);
    }

    @Test
    public void testVerifyTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());
    }

    @Test
    public void testDeleteElementFromTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        Integer count = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_LINK)).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(--count);
    }

    @Test
    public void testDeleteAllElementsFromTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        assertThat(convertListToLocator(TABLE_LIST).size()).isNotEqualTo(0);

        scrollPage();
        waiter(3000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_BUTTON)).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(0);
    }

    @Test
    public void testUploadCorrectSizeFile() {
        File file = new File("src/main/resources/upload.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.json"));
    }

    @Test
    public void testUploadInCorrectSizeFile() {
        File file = new File("src/main/resources/10mb-sample.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(ERROR_UPLOAD_MESSAGE, "1MB"));
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void scrollPage() {
        JavascriptExecutor js = ((JavascriptExecutor) webDriver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private void waiter(final Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<WebElement> convertListToLocator(By locator) {
        return webDriver.findElements(locator);
    }
}
