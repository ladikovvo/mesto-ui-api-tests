package com.company.db;

import com.company.mesto.testdata.CommonTestData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("db")
public class DbTests {


    private final DbClient db = new DbClient();


    @Test
    void shouldConnectToDb() {
        assertEquals(1, db.selectOne());
    }

    @Test
    void shouldInsertNewValue(){
        String email = CommonTestData.randomEmail();
        try {
            db.insertTestUser(email);
            assertTrue(db.testUserExists(email));
        } finally {
            db.deleteTestUserByEmail(email);
        }
    }

    @Test
    void shouldReturnLatestInsertedEmail(){
        String email = CommonTestData.randomEmail();
        db.insertTestUser(email);
        try{ List<String> emails = db.getLatestTestUsers(1);
        assertEquals(email, emails.get(0));
        } finally {
            db.deleteTestUserByEmail(email);
        }

    }

    @Test
    void shouldIncreaseCountAfterInsert() {
        String email = CommonTestData.randomEmail();
        int before = db.countTestUsers();

        try {
            db.insertTestUser(email);
            int after = db.countTestUsers();
            assertEquals(before + 1, after);
        } finally {
            db.deleteTestUserByEmail(email);
        }
    }

}
