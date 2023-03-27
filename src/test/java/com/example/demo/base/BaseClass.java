package com.example.demo.base;

import com.example.demo.browser.Chrome;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static com.example.demo.pages.AbstractPages.*;

public class BaseClass {
    private static final int DELAY = 10;

    @BeforeAll
    public static void start() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        final Chrome chrome = new Chrome();
        DriverHolder.setDriverThread(chrome.createDriver(capabilities));
        DriverHolder.getDriverThread().manage().timeouts().implicitlyWait(DELAY, TimeUnit.SECONDS);
        DriverHolder.getDriverThread().manage().timeouts().pageLoadTimeout(DELAY, TimeUnit.SECONDS);
        DriverHolder.getDriverThread().manage().timeouts().setScriptTimeout(DELAY, TimeUnit.SECONDS);
        navigateWebBrowser().to("http://localhost:8080/");
    }

    @AfterAll
    public static void stop() {
        clearLocalStorage();
        closeWebBrowser();
    }
}
