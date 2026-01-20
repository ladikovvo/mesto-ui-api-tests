package com.company.mesto.ui.utils;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class Html5Validation {
    private Html5Validation() {}

    public static void shouldBeInvalid(SelenideElement field) {


        Boolean invalid = executeJavaScript(
                "return arguments[0].matches(':invalid');",
                field
        );

        assertTrue(Boolean.TRUE.equals(invalid), "Expected field to be :invalid (HTML5 validation)");
    }

    public static String validationMessage(SelenideElement field) {
        field.shouldBe(visible);
        return executeJavaScript("return arguments[0].validationMessage;", field);
    }
}

