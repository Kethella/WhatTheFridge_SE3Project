package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.enums.SecurityQuestion;
import de.hdm.se3project.backend.repository.AccountRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;  //getting instance of the Account Repository

    @AfterEach
    void cleanUp(){
        this.accountRepository.deleteAll();
    }

    @Test
    @Description("Verifies if container is empty before save Account")
    void listOfItemsShouldBeEmpty(){
        List<Account> accounts = accountRepository.findAll();
        Assertions.assertEquals(0, accounts.size());
    }

    @Test
    @Description("Save Account in the Database")
    void shouldSaveAccount(){
        Account newAccount = new Account();

        newAccount.setName("NewUser");
        newAccount.setEmail("NewUser@mailmail.com");
        newAccount.setPassword("123654");
        newAccount.setSecurityQuestion(SecurityQuestion.Q5);
        newAccount.setSecurityAnswer("NewAnswer");
        newAccount.setPersonalRecipes(newAccount.getPersonalRecipes());
        newAccount.setFridgeItems(newAccount.getFridgeItems());

        Account account1 = this.accountRepository.save(newAccount); //not saving in the actual Database, because container is being used
        Assertions.assertEquals("NewUser", account1.getName());
    }

    @Test
    @Description("Check if the List of Account has the size accordingly to the amount of saved Accounts")
    void shouldCheckListSizeOfAccount() {
        Account account1 = new Account("1", "user", "user@mailmail.com", "123456",
                SecurityQuestion.Q2, "Buddy", null, null);
        Account account2 = new Account("2", "user2", "user2@mailmail.com", "987654",
                SecurityQuestion.Q1, "Math", null, null);

        this.accountRepository.save(account1);
        this.accountRepository.save(account2);

        List<Account> accounts = accountRepository.findAll();
        Assertions.assertEquals(2, accounts.size());
    }

    @Test
    @Description("Check if the List of Account has the size accordingly to the amount of saved minus deleted accounts")
    void shouldDeleteAccount() {
        Account account1 = new Account("1", "user", "user@mailmail.com", "123456",
                SecurityQuestion.Q2, "Buddy", null, null);
        Account account2 = new Account("2", "user2", "user2@mailmail.com", "987654",
                SecurityQuestion.Q1, "Math", null, null);

        this.accountRepository.save(account1);
        this.accountRepository.save(account2);

        this.accountRepository.delete(account1);

        List<Account> accounts = accountRepository.findAll();
        Assertions.assertEquals(1, accounts.size());
    }
}
