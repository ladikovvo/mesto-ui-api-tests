package com.company.mesto.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.company.mesto.ui.utils.AllureAttachments;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RegistrationPage {
    private final SelenideElement registrateButton = $$("button.auth-form__button").findBy(exactText("Зарегистрироваться"));
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement passwordInput = $("#password");

    private final SelenideElement headerSignInButton = $(".header__auth-link");
    private final SelenideElement signInButton = $(".auth-form__link");


    @Step("Registration page should be opened")
    public RegistrationPage shouldBeOpened(){
        registrateButton.shouldBe(visible);
        AllureAttachments.screenshot("After open Registration page");
        return this;
    }

    @Step("Register new user: {email} / ***")
    public HomePage registerNewUser(String email, String password){
        emailInput.shouldBe(interactable).setValue(email);
        passwordInput.shouldBe(interactable).setValue(password);
        registrateButton.shouldBe(interactable).click();
        return new HomePage();
    }





}
