package com.example.demo.smoke;

import com.example.demo.base.BaseClass;
import com.example.demo.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleRegisterNewUserTest extends BaseClass {

    @Description("This is simple register automation script.")
    @DisplayName("Simple register automation script.")
    @Test
    public void simpleRegisterNewUserTest() {
        new MainPage()
                .openRegisterPage()
                .addCredentialForRegister()
                .addOneTimePasswordCode();
    }
}
