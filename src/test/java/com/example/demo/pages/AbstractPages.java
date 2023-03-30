package com.example.demo.pages;

import com.example.demo.base.DriverHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;
import java.util.List;

public abstract class AbstractPages {

    public static WebDriver.Navigation navigateWebBrowser() {
        return DriverHolder.getDriverThread().navigate();
    }

    public static void closeWebBrowser() {
        DriverHolder.getDriverThread().manage().deleteAllCookies();
        DriverHolder.getDriverThread().quit();
    }

    public static void clearLocalStorage() {
        WebStorage webStorage = (WebStorage) DriverHolder.getDriverThread();
        webStorage.getLocalStorage().clear();
    }

    public AbstractPages scrollDownPage() {
        final JavascriptExecutor executor = (JavascriptExecutor) DriverHolder.getDriverThread();

        executor.executeScript("window.scrollTo(0,document.body.scrollHeight);");

        return this;
    }

    public void waiter(final Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<WebElement> convertListToLocator(By locator) {
        return DriverHolder.getDriverThread().findElements(locator);
    }

    public Select selectDropDown(final By locator) {
        return new Select(DriverHolder.getDriverThread().findElement(locator));
    }

    public String createEmail() {
        return "test_" + new Date().getTime() + "@qa.team";
    }
}
