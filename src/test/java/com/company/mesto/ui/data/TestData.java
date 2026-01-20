package com.company.mesto.ui.data;

import java.util.UUID;

public class TestData {
    private TestData() {};

    public static final String EMAIL = "jsllv@mail.ru";
    public static final String PASSWORD = "3329";
    public static final String INVALID_EMAIL = "123";
    public static final String INVALID_PASSWORD = "123";
    public static final String PROFILE_NAME = "Жак-Ив Кусто";
    public static final String PROFILE_ACTIVITY = "Исследователь";

    public static String randomString() { return UUID.randomUUID().toString().replace("-", "").substring(0,10); }

    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().replace("-", "") + "@test.com";
    }

    public static String randomPassword() {
        // 12+ символов, буквы+цифры
        return "P@" + UUID.randomUUID().toString().replace("-", "").substring(0, 14) + "1";
    }



}
