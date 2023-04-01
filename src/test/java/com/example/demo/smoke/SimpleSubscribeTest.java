package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleSubscribeTest extends BaseClass {
    @Description("This is simple subscribe automation script.")
    @DisplayName("Simple subscribe automation script.")
    @Test
    public void simpleAuthorizationTest() {
        new MainPage()
                .openMenuMainPage()
                .addSubscribeEmail("subscribeEmail@qa.team")
                .checkSubscribe();
    }
}
