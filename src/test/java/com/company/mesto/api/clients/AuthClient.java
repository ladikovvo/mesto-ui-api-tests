package com.company.mesto.api.clients;

import com.company.mesto.api.config.ApiConfig;
import io.restassured.http.ContentType;

import java.io.InputStream;

import static io.restassured.RestAssured.*;

public class  AuthClient {

    public String loginAndGetToken() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("payloads/auth/login.json")) {

            if (is == null) {
                throw new IllegalStateException("login.json not found in resources/payloads/auth/");
            }

            String token = given()
                    .baseUri(ApiConfig.BASE_URI)
                    .basePath(ApiConfig.BASE_PATH)
                    .contentType(ContentType.JSON)
                    .body(is)
                    .when().post("/signin")
                    .then()
                    .statusCode(200)
                    .extract().path("token");

            if (token == null || token.isBlank()) {
                throw new IllegalStateException("Token is null/blank after /signin");
            }
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Auth failed: cannot get token", e);
        }
    }
}
