package com.example.demo.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface WebDriverProvider {
    WebDriver createDriver(DesiredCapabilities capabilities);

}
