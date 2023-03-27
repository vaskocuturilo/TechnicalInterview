package com.example.demo.pages;

import com.example.demo.base.DriverHolder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;

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
}
