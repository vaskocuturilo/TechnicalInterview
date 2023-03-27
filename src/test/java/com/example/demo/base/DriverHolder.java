package com.example.demo.base;

import org.openqa.selenium.WebDriver;

public class DriverHolder {
    private static final ThreadLocal<WebDriver> DRIVER_THREAD = new ThreadLocal<>();

    public static WebDriver getDriverThread() {
        return DRIVER_THREAD.get();
    }

    public static void setDriverThread(final WebDriver webDriverValue) {
        DRIVER_THREAD.set(webDriverValue);
    }
}
