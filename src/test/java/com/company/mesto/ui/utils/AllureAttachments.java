package com.company.mesto.ui.utils;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class AllureAttachments {
    private AllureAttachments() {}


    @Attachment(value = "{name}", type = "image/png")
    public static byte[] screenshot(String name) {
        if (!WebDriverRunner.hasWebDriverStarted()) return new byte[0];
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
