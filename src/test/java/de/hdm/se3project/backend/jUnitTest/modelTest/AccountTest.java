package de.hdm.se3project.backend.jUnitTest.modelTest;

import de.hdm.se3project.backend.models.Account;
import de.hdm.se3project.backend.models.enums.SecurityQuestion;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Description("Testing model class: Account")
public class AccountTest {
    Account account;

    Account ACCOUNT_1 = new Account("1", "userName", "user@mailmail.com", "123456",
            SecurityQuestion.Q1, "Chemistry", null, null);

    @BeforeEach
    void setUp() {account = new Account();}

    @Test
    void accountTest() {
        account = ACCOUNT_1;
        assertEquals("1", account.getId());
        assertEquals("userName", account.getName());
        assertEquals("user@mailmail.com", account.getEmail());
        assertEquals("123456", account.getPassword());
        assertEquals(SecurityQuestion.Q1, account.getSecurityQuestion());
        assertEquals("Chemistry", account.getSecurityAnswer());
    }

    @Test
    void accountNotNullTest() {
        account = ACCOUNT_1;
        assertNotNull(account.getId());
        assertNotNull(account.getName());
        assertNotNull(account.getEmail());
        assertNotNull(account.getPassword());
        assertNotNull(account.getSecurityQuestion());
        assertNotNull(account.getSecurityAnswer());
    }
}
