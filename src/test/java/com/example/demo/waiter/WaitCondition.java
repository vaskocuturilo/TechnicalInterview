package com.example.demo.waiter;

import com.example.demo.base.DriverHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitCondition {
    private static final int DELAY = 10;

    private WebDriverWait webDriverWait;

    public WaitCondition() {
        webDriverWait = new WebDriverWait(DriverHolder.getDriverThread(), Duration.ofSeconds(DELAY));
    }

    public WebElement waitForVisibilityOfElementLocatedBy(final By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Boolean waitForInvisibilityOfElementLocated(final By locator) {
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
