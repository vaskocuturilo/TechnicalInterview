package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUITest {
    private static WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private static final int DELAY = 10;
    private static By TASK_NAME = By.xpath("//input[@name='taskName']");
    private static By DESCRIPTION = By.xpath("//input[@name='description']");
    private static By SUBMIT_BUTTON = By.xpath("//input[@value='Add task']");
    private static By DELETE_BUTTON = By.xpath("//a[contains(text(),'Delete')]");
    private static By TABLE = By.xpath("//td[contains(text(), 'Delete from list')]");

    private static By MAIN_PAGE_LINK_QUESTIONS = By.xpath("//a[@id='list_questions_link']");
    private static By TABLE_TITLE = By.xpath("//h2[contains(text(),'Technical interview questions')]");

    private static By UPLOAD_FILE = By.xpath("//input[@id='upload_file']");

    private static By UPLOAD_FILE_BUTTON = By.xpath("//input[@id='upload_file_button']");

    private static By MESSAGE = By.xpath("//h3[@id='upload_message']");

    private static final String TEXT = "Technical interview questions: (questions count : %s)";

    private static final String UPLOAD_MESSAGE = "You successfully uploaded file is: %s";

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
    }

    @Test
    public void testVerifyTitle() {
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");
    }

    @Test
    public void testVerifyAddElementsOnPage() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE_TITLE)).getText()).isEqualTo(String.format(TEXT, "1"));
    }

    @Test
    public void testVerifyTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TASK_NAME)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());
    }

    @Test
    public void testDeleteElementFormTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE_TITLE)).getText()).isEqualTo(String.format(TEXT, "1"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE_TITLE)).getText()).isEqualTo(String.format(TEXT, 0));
    }

    @Test
    public void testUploadFile() {
        File file = new File("src/main/resources/upload.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.json"));
    }


    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
