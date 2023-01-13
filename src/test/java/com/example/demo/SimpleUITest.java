package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUITest {

    private static WebDriver webDriver;

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
    public void testVerifyElementsOonPage() {
     //TODO Test
    }

    @Test
    public void testVerifyTable() {
        //TODO Test
    }

    @AfterAll
    public static void tearDown() {
        webDriver.quit();
    }
}
