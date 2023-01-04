package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

@Testcontainers//need to enable to run the tc Junit 5 into test container mode --> it runs all containers annotated with @container
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
//@SpringBootTest
public class FridgeItemRepositoryTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;  //getting instance of the Fridge Item Repository

    @Container  //creating a mongoDB container obj and keeping it until the end of all tests, then it will be deleted
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest")); //class MongoDBContainer coming through library passing desired Docker image

    @DynamicPropertySource //Connecting to our local dockerized MongoDB instance
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @BeforeAll //starting the container
    static void initAll(){
        container.start();
    }

    @AfterEach
    void cleanUp(){
        this.fridgeItemRepository.deleteAll();
    }

    @Test
    @Description("Check if Container has started and the public port is available")
    void containerStartsAndPublicPortIsAvailable() {
        assertThatPortIsAvailable(container);
    }

    @Test
    @Description("Verifies if container is empty before save item")
    void listOfItemsShouldBeEmpty(){
        List<FridgeItem> fridgeItems = fridgeItemRepository.findAll();
        Assertions.assertEquals(0, fridgeItems.size());
    }

    @Test
    @Description("Save one Fridge Item in the Database")
    void shouldSaveFridgeItem(){
        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("apple");
        fridgeItem.setAmount(2);
        fridgeItem.setExpirationDate("13.01.2023");
        fridgeItem.setOwnerAccount("1");

        FridgeItem fridgeItem1 = this.fridgeItemRepository.save(fridgeItem); //not saving in the actual Database, because container is being used
        Assertions.assertEquals("apple", fridgeItem1.getName());
    }

    @Test
    @Description("Check if the List of FridgeItem has the size accordingly to the amount of saved items")
    void shouldCheckListSizeOfFridgeItem() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> fridgeItems = fridgeItemRepository.findAll();
        Assertions.assertEquals(2, fridgeItems.size());
    }

    @Test
    @Description("Check if the List of FridgeItem has the size accordingly to the amount of saved minus deleted items")
    void shouldDeleteItem() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        this.fridgeItemRepository.delete(fridgeItem1);

        List<FridgeItem> fridgeItems = fridgeItemRepository.findAll();
        Assertions.assertEquals(1, fridgeItems.size());
    }

    private void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in host and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getHost(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }

}
