package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.base.DriverHolder;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMainPageTest extends BaseClass {
    @Test
    public void testSimpleVerifyTitleMainPage() {
        assertThat(DriverHolder.getDriverThread().getTitle()).isEqualTo("Technical interview questions");
    }

    @Description("This is simple login automation script.")
    @DisplayName("Simple login automation script.")
    @Test
    public void testCheckModalWindowMainPage() {
        new MainPage()
                .openMenuMainPage()
                .checkModalWindow();
    }
}
