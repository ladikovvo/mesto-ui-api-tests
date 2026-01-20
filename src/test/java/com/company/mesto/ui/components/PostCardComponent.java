package com.company.mesto.ui.components;

import com.codeborne.selenide.SelenideElement;
import com.company.mesto.ui.utils.AllureAttachments;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;

public class PostCardComponent {
    private final SelenideElement post;
    private final SelenideElement title;
    private final SelenideElement likesCount;
    private final SelenideElement likeButton;

    public PostCardComponent(SelenideElement post) {
        this.post = post;
        this.title = this.post.$("h2.card__title");
        this.likesCount = this.post.$("p.card__like-count");
        this.likeButton = this.post.$("button.card__like-button");
    }






    private final String likeButtonTrue = "card__like-button_is-active";



    public String title(){
        return title
                .shouldBe(visible)
                .getText()
                .trim();
    }


    @Step("Read likes counter")
    public int likesCount(){
        String text = likesCount
                .shouldBe(visible)
                .getText()
                .trim();

        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }



    @Step("Wait likes counter changed from {old}")
    public PostCardComponent waitLikesChangedFrom(int old) {
        likesCount.shouldBe(visible).shouldNotHave(exactText(String.valueOf(old)));
        AllureAttachments.screenshot("After likes counter changed");
        return this;
    }

    @Step("Ensure post is not liked")
    public PostCardComponent ensureNotLiked(){
        if(likeButton.shouldBe(visible).has(cssClass(likeButtonTrue))){
            unlikePost();
        }
        AllureAttachments.screenshot("After ensure post is not liked");
        return this;
    }

    @Step("Ensure post is liked")
    public PostCardComponent ensureLiked(){
        if(!likeButton.shouldBe(visible).has(cssClass(likeButtonTrue))){
            likePost();
        }
        AllureAttachments.screenshot("After ensure post is liked");
        return this;
    }



    @Step("Like post")
    public PostCardComponent likePost(){
        likeButton
                .shouldBe(interactable)
                .shouldNotHave(cssClass(likeButtonTrue))
                .click();

        likeButton.shouldHave(cssClass(likeButtonTrue));
        return this;

    }

    @Step("Unlike post")
    public PostCardComponent unlikePost() {


        likeButton.shouldBe(interactable)
                .shouldHave(cssClass(likeButtonTrue))
                .click();

        likeButton.shouldNotHave(cssClass(likeButtonTrue));
        AllureAttachments.screenshot("After ensure post is unliked");
        return this;
    }




}
