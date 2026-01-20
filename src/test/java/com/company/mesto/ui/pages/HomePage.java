package com.company.mesto.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.company.mesto.ui.components.PostCardComponent;
import com.company.mesto.ui.utils.AllureAttachments;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class HomePage {

    // main Info
    private final SelenideElement headerUser = $(".header__user");
    private final SelenideElement profileName = $("h1.profile__title");
    private final SelenideElement profileDescription = $("p.profile__description");
    private final SelenideElement footerText = $(".footer__copyright");

    // main buttons
    private final SelenideElement logoutButton = $(".header__logout");
    private final SelenideElement addPostButton = $(".profile__add-button");
    private final SelenideElement editProfileButton = $(".profile__edit-button");
    private final SelenideElement editProfileImageButton = $(".profile__image");

    // edit profile Popup
    private final SelenideElement editProfilePopup = $(".popup.popup_type_edit");
    private final SelenideElement inputProfileName = editProfilePopup.$("input#owner-name");
    private final SelenideElement inputProfileDescription = editProfilePopup.$("input#owner-description");
    private final SelenideElement saveProfileButton = editProfilePopup.$$("button").findBy(text("Сохранить"));
    private final SelenideElement closeEditProfilePopup = editProfilePopup.$("button.popup__close");
    private final String popupIsOpened = "popup_is-opened";

    // registration Popup
    private final SelenideElement registrationPopup = $(".popup.popup_is-opened");
    private final SelenideElement registrationStatusMessage = registrationPopup.$(".popup__status-message");
    private final SelenideElement closeRegistrationPopup = registrationPopup.$(".popup__close");

    // posts
    private final SelenideElement listOfPosts = $("ul.places__list");
    private final ElementsCollection cards = listOfPosts.$$("li.places__item.card");


    private SelenideElement findPostByTitle(String title){
        listOfPosts.shouldBe(visible);
        cards.shouldHave(sizeGreaterThan(0));

        for (SelenideElement card : cards) {
            SelenideElement titleEl = card.$("h2.card__title");
            if (titleEl.exists() && titleEl.getText().trim().equals(title)) {
                return card;
            }
        }
        throw new AssertionError("Post card not found by title: " + title);
    }




    @Step("Home page should be opened")
    public HomePage shouldBeOpened(){
        logoutButton.shouldBe(visible);
        AllureAttachments.screenshot("After open Home page");
        return this;
    }

    @Step("Logout")
    public LoginPage clickLogout(){
        logoutButton.shouldBe(interactable).click();
        return new LoginPage();
    }

    @Step("Get post card by title: {title}")
    public PostCardComponent cardByTitle(String title){
        SelenideElement card = findPostByTitle(title)
                .shouldBe(visible)
                .scrollIntoView(true);
        AllureAttachments.screenshot("After get post card by title");
        return new PostCardComponent(card);
    }

    @Step("Get user email from header")
    public String getHeaderUserEmail(){
        return headerUser
                .shouldBe(visible)
                .shouldNotBe(empty)
                .getText()
                .trim();

    }




    // Pofile settings -----------------------------------------------------


    @Step("Get profile name")
    public String getProfileName(){
        return profileName
                .shouldBe(visible)
                .shouldNotBe(empty)
                .getText()
                .trim();
    }

    @Step("Get profile activity")
    public String getProfileActivity(){
        return profileDescription
                .shouldBe(visible)
                .shouldNotBe(empty)
                .getText()
                .trim();
    }

    @Step("Open popup - Edit profile")
    public HomePage openEditProfilePopup(){
        editProfileButton.shouldBe(interactable).click();
        editProfilePopup.shouldHave(cssClass(popupIsOpened));
        inputProfileName.shouldBe(interactable);
        AllureAttachments.screenshot("After edit profile popup opened");
        return this;
    }


    @Step("Fill profile Name: {name}")
    public HomePage editProfileName(String name){
        inputProfileName.shouldBe(interactable).setValue(name);
        AllureAttachments.screenshot("After profile name set");
        return this;
    }

    @Step("Fill profile Activity: {activity}")
    public HomePage editProfileActivity( String activity){
        inputProfileDescription.shouldBe(interactable).setValue(activity);
        AllureAttachments.screenshot("After get post card by title");
        return this;
    }

    @Step("Save profile")
    public  HomePage saveProfile(){
        saveProfileButton.shouldBe(interactable).click();
        editProfilePopup.shouldNotHave(cssClass(popupIsOpened));
        AllureAttachments.screenshot("After profile info saved");
        return this;
    }

    @Step("Close popup - Edit profile")
    public HomePage closeEditProfilePopup(){
        closeEditProfilePopup.shouldBe(interactable).click();
        editProfilePopup.shouldNotHave(cssClass(popupIsOpened));
        AllureAttachments.screenshot("After edit profile popup closed");
        return this;
    }

    @Step("Wait profile Name changed")
    public HomePage waitProfileNameChanged(String old) {
        profileName.shouldNotHave(exactText(String.valueOf(old)));
        AllureAttachments.screenshot("After profile Name changed");
        return this;
    }

    @Step("Wait profile Activity changed")
    public HomePage waitProfileActivityChanged(String old) {
        profileDescription.shouldNotHave(exactText(String.valueOf(old)));
        AllureAttachments.screenshot("After profile Activity changed");
        return this;
    }








    // REGISTRATION METHODS --------------------------------------------

    @Step("Expect registration message : {message}")
    public HomePage shouldShowRegistrationMessage(String message){
        registrationPopup.shouldBe(visible);
        registrationStatusMessage.shouldHave(text(message));
        AllureAttachments.screenshot("After find Expected registration message");
        return this;
    }

    @Step("Close registration status popup")
    public HomePage closeRegistrationStatusMessage(){
        closeRegistrationPopup.shouldBe(interactable).click();
        registrationPopup.shouldNot(exist);
        AllureAttachments.screenshot("After close registration status popup");
        return this;
    }






}
