package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.Application;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.enums.SecurityQuestion;
import de.hdm.se3project.backend.repository.AccountRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

// @Testcontainers//need to enable to run the tc Junit 5 into test container mode --> it runs all containers annotated with @container
// @DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;  //getting instance of the Account Repository

//    @Container  //creating a mongoDB container obj and keeping it until the end of all tests, then it will be deleted
//    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest")); //class MongoDBContainer coming through library passing desired Docker image
//
//    @DynamicPropertySource //Connecting to our local dockerized MongoDB instance
//    public static void setProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
//    }
//
//    @BeforeAll //starting the container
//    static void initAll(){
//        //container.start();
//    }
//
    @AfterEach
    void cleanUp(){
        this.accountRepository.deleteAll();
    }
//
//    @Test
//    @Description("Check if Container has started and the public port is available")
//    void containerStartsAndPublicPortIsAvailable() {
//        assertThatPortIsAvailable(container);
//    }

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

    /*private void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in host and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getHost(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }*/

}
