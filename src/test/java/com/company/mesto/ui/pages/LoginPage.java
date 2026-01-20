package com.company.mesto.ui.pages;
import com.codeborne.selenide.SelenideElement;
import com.company.mesto.ui.utils.AllureAttachments;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginPage {
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement signInButton = $(".auth-form__button");

    private final SelenideElement registButton = $("a.header__auth-link");

    private final SelenideElement errorPopup = $(".popup.popup_is-opened");
    private final SelenideElement errorText = errorPopup.$(".popup__status-message");
    private final SelenideElement closeErrorButton = errorPopup.$("button.popup__close");

    private final SelenideElement footerText = $(".footer__copyright");

    @Step("Open Login page")
    public LoginPage openPage(){
        open("/signin");
        signInButton.shouldBe(visible);
        AllureAttachments.screenshot("After open Login page");
        return this;
    }

    @Step("Login page should be opened")
    public LoginPage shouldBeOpened(){
        signInButton.shouldBe(visible);
        AllureAttachments.screenshot("After open Login page");
        return this;
    }

    public LoginPage clickSignIn(){
        signInButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Fill credentials: {email} / ***")
    public LoginPage fillCredential(String email, String password){
        emailInput.shouldBe(visible).setValue(email);
        passwordInput.shouldBe(visible).setValue(password);
        AllureAttachments.screenshot("After fill credentials");
        return this;
    }

    @Step("Login with email: {email}")
    public HomePage login(String email, String password) {
        fillCredential(email, password);
        clickSignIn();
        return new HomePage();
    }

    @Step("Close Error popup")
    public LoginPage closeErrorPopup(){
        errorPopup.shouldBe(visible);
        closeErrorButton.shouldBe(enabled).click();
        errorPopup.shouldNot(exist);
        AllureAttachments.screenshot("After close error popup");
        return this;
    }


    @Step("Expect error popup: {line1} {line2}")
    public LoginPage shouldShowError(String line1, String line2) {
        errorPopup.shouldBe(visible);
        errorText.shouldHave(text(line1))
                .shouldHave(text(line2));
        AllureAttachments.screenshot("After find expected error popup");
        return this;
    }

    @Step("Open registration page")
    public RegistrationPage clickRegistration(){
        registButton.shouldBe(interactable).click();
        return new RegistrationPage();
    }


    public SelenideElement getEmailInput() {
        return emailInput;
    }

    public SelenideElement getPasswordInput() {
        return passwordInput;
    }
}
