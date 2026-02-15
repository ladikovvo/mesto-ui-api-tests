package com.company.mesto.api.clients;

import com.company.mesto.api.models.ApiResponse;
import com.company.mesto.api.models.CreateCardRequest;
import com.company.mesto.api.models.Card;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class CardsClient {

    private final RequestSpecification req;

    public CardsClient(RequestSpecification req) {
        this.req = req;
    }

    private Response createCard(String name, String link) {
        CreateCardRequest card = new CreateCardRequest(name, link);
        return given().spec(req)
                .body(card)
                .when()
                .post("/cards");

    }

    @Step("Create card (name={name}) and return card")
    public Card createCard_GetCard(String name, String link) {
        ApiResponse<Card> parse = createCard(name, link)
                .then()
                .statusCode(201)
                .extract().as(new TypeRef<ApiResponse<Card>>() {
                });
        assertThat(parse.getData(), notNullValue());

        Card card = parse.getData();
        assertThat(card.getId(), not(emptyOrNullString()));
        assertEquals(name ,card.getName());
        assertEquals(link , card.getLink());

        return card;
    }

    @Step("Create card expecting 400 and return validation keys")
    public List<String> createCardExpect400_returnValidationKeys(String name, String link) {
        CreateCardRequest card = new CreateCardRequest(name, link);

        return given().spec(req)
                .body(card)
                .when()
                .post("/cards")
                .then()
                .statusCode(400)
                .body("message", not(emptyOrNullString()))
                .body("validation.keys", not(empty()))
                .extract().path("validation.keys");
    }

    @Step("Delete card by id={id}")
    public Response deleteCard(String id) {
        return given().spec(req)
                .when()
                .delete("/cards/{id}", id);
    }



    @Step("Get all cards")
    public List<Card> getAllCards() {

        ApiResponse<List<Card>> parsed =
                given().spec(req)
                        .when()
                        .get("/cards")
                        .then()
                        .statusCode(200)
                        .body("data", notNullValue())
                        .extract().as(new TypeRef<ApiResponse<List<Card>>>() {});

        return parsed.getData();
    }

    @Step("Get first card from list")
    public Card getFirstCardFromList(){
        List<Card> cards = getAllCards();
        assertFalse(cards.isEmpty(), "Cards list is empty");
        return cards.get(0);
    }


    @Step("Get all card ids")
    public List<String> getAllCardIds() {
        return getAllCards().stream()
                .map(Card::getId)
                .toList();
    }

    @Step("Get card by id")
    public Card getCardById(String id){
        List<Card> cards = getAllCards();
        return cards.stream()
                .filter(card -> card.getId().equals(id)).findFirst().orElseThrow(() -> new AssertionError("Card not found"));
    }

    @Step("Should not contain card id={id}")
    public void shouldNotContainCardId(String id){
        List<String> ids = getAllCardIds();
        assertFalse(ids.contains(id), "Expected card id to be absent in /cards list, but it was found: " + id);
    }


}
