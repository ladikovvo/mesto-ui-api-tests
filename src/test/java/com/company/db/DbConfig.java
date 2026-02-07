package com.company.db;

public class DbConfig {
    public static String url() {
        return System.getProperty("db.url",
                System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/mesto"));
    }

    public static String user() {
        return System.getProperty("db.user",
                System.getenv().getOrDefault("DB_USER", "mesto"));
    }

    public static String pass() {
        return System.getProperty("db.pass",
                System.getenv().getOrDefault("DB_PASS", "mesto"));
    }
}
