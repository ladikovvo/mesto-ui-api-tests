package com.company.mesto.ui.tests;

import com.company.mesto.ui.config.TestBase;
import com.company.mesto.ui.data.TestData;
import com.company.mesto.ui.pages.LoginPage;
import com.company.mesto.ui.pages.RegistrationPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("auth")
public class RegistrationTests extends TestBase {

    private RegistrationPage registrationPage;

    @BeforeEach
    void openRegistrationPage(){
        registrationPage = new LoginPage()
                .openPage()
                .clickRegistration()
                ;
    }

    @Test
    @DisplayName("Register new valid user")
    void registerNewValidUser(){
                registrationPage
                        .registerNewUser(TestData.randomEmail(), TestData.randomPassword())
                        .shouldBeOpened()
                        .shouldShowRegistrationMessage("Вы успешно зарегистрировались");

    }



    @Test
    @DisplayName("Close registration message")
    void closeRegistrationMessage(){
        registrationPage
                .registerNewUser(TestData.randomEmail(), TestData.randomPassword())
                .closeRegistrationStatusMessage()
                .shouldBeOpened();
    }



}
