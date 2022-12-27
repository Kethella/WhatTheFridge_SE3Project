package de.hdm.se3project.backend.controller.IntegrationTest.repository;

import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;

@Testcontainers //need to enable to run the tc Junit 5 into test container mode
@SpringBootTest //Notation to make it run as a test
public class FridgeItemRepositoryTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;  //getting instance of the fIRepos.

    @Container  //creating a mongoDB container obj
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:6.0.3")); //class
    // MongoDBContainer coming through library passing desired Docker image

     @BeforeAll //starting the container
     static void initAll(){
         container.start();
     }

    @Test
    void containerStartsAndPublicPortIsAvailable() {
        assertThatPortIsAvailable(container);
    }

    @Test
    //save one FI in the DB
    void saveFridgeItem(){
        FridgeItem fridgeItem = new FridgeItem();
        fridgeItem.setName("apple");
        fridgeItem.setAmount(2);
        fridgeItem.setExpirationDate("13.01.2023");
        fridgeItem.setOwnerAccount("1");

        FridgeItem fridgeItem1 = fridgeItemRepository.save(fridgeItem); //not saving in the actual Database,
        // because container is beeing used
        assert fridgeItem1 != null;
        Assertions.assertEquals("apple", fridgeItem1.getName());
    }

    private void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in IP Address and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getContainerIpAddress(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }

}
