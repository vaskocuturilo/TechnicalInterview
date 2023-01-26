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

    private static By TABLE_TITLE = By.xpath("//h2[contains(text(),'Technical interview questions')]");

    private static final String TEXT = "Technical interview questions: (questions count : %s)";


    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpDriver() {
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:8080/");
    }

    @Test
    public void testVerifyTitle() {
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions.");
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
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions.");
        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE_TITLE)).getText()).isEqualTo(String.format(TEXT, "1"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE_TITLE)).getText()).isEqualTo(String.format(TEXT, 0));
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}
