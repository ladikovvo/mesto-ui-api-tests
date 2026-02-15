package com.company.mesto.api.tests;

import com.company.mesto.api.clients.CardsClient;
import com.company.mesto.api.models.Card;
import com.company.mesto.api.models.UserMe;
import com.company.mesto.testdata.CommonTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.List;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("api")
@DisplayName("Api tests")
public class ApiTests extends ApiTestBase {

    @DisplayName("User me tests")
    @Nested
    class UserMeTests {

        @DisplayName("Check user name not null")
        @Test
        void checkUserName() {
            given().spec(req)
                    .get("/users/me")
                    .then().spec(ok200)
                    .assertThat().body("data.name", notNullValue());
        }

        @DisplayName("Update profile and Roll it back")
        @Test
        void updateProfile_andRollback(){

            UserMe me = usersClient.getMe();
            String oldName = me.getName();
            String oldAbout = me.getAbout();

            String newName = CommonTestData.randomString();
            String newAbout = CommonTestData.randomString();

            try{
                usersClient.updateMe(newName, newAbout);
                usersClient.shouldHaveProfile(newName, newAbout);

            } finally {
                usersClient.updateMe(oldName, oldAbout);
                usersClient.shouldHaveProfile(oldName, oldAbout);
            }

        }


    }

    @DisplayName("Card tests")
    @Nested
    class CardTests {

        @DisplayName("Add ned card and delete it")
        @Test
        void addNewCard_andDeleteIt(){
            String postName = CommonTestData.randomString();

            Card card = cardsClient.createCard_GetCard(postName , CommonTestData.POST_LINK);
            String id = card.getId();
            cardsClient.deleteCard(id)
                    .then()
                    .spec(ok200);
            cardsClient.shouldNotContainCardId(id);
        }

        @DisplayName("Get all cards should Not return empty list")
        @Test
        void getAllCardsShouldNotReturnEmptyList(){
            List<Card> cards = cardsClient.getAllCards();
            assertFalse(cards.isEmpty());
        }

        @DisplayName("First card should have mandatory fields")
        @Test
        void firstCardShouldHaveMandatoryFields(){
            Card card = cardsClient.getFirstCardFromList();
            String id = card.getId();
            String name = card.getName();
            String link = card.getLink();

            assertAll("Card fields",
                    () -> assertThat("ID not null or empty", id, not(emptyOrNullString())),
                    () -> assertThat("Name not null or empty", name, not(emptyOrNullString())),
                    () -> assertThat("Link not null or empty", link, not(emptyOrNullString())));
        }

        @DisplayName("Add new card should save correct value")
        @Test
        void addNewCardShouldSaveCorrectValue_andDeleteIt(){
            String name = CommonTestData.randomString();
            String link = CommonTestData.POST_LINK;

            Card newCard = cardsClient.createCard_GetCard(name, link);
            String id = newCard.getId();

            try {
                Card actualCard = cardsClient.getCardById(id);
                assertEquals(name, actualCard.getName(), "Name does not correspond");
                assertEquals(link, actualCard.getLink(), "Link does not correspond");
            } finally{
                cardsClient.deleteCard(id)
                        .then()
                        .spec(ok200);
                cardsClient.shouldNotContainCardId(id);

            }





        }

    }

}
