package de.hdm.se3project.backend.controller.integrationTest.servicesTest;


import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
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
public class FridgeItemServiceTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;
    private FridgeItemServiceImpl FridgeItemServiceImpl;


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

    @BeforeEach
    void setUp(){
        this.FridgeItemServiceImpl = new FridgeItemServiceImpl(fridgeItemRepository);
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
    @Description("Checks method getFridgeItems in FridgeItemServiceImpl class")
    void shouldGetFridgeItems() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> result = FridgeItemServiceImpl.getFridgeItems();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @Description("Checks method updateFridgeItem in FridgeItemServiceImpl class")
    void shouldUpdateFridgeItem() throws ResourceNotFoundException {
        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("chocolate milk");
        fridgeItem.setAmount(1);
        fridgeItem.setExpirationDate("19.01.2023");
        fridgeItem.setOwnerAccount("3");

        FridgeItem fridgeItem1 = this.fridgeItemRepository.save(fridgeItem);

        fridgeItem1.setAmount(3);

        FridgeItem fridgeItem2 = this.FridgeItemServiceImpl.updateFridgeItem(fridgeItem.getId(), fridgeItem1);
        Assertions.assertEquals(3, fridgeItem2.getAmount());
        Assertions.assertEquals("chocolate milk", fridgeItem2.getName());
        Assertions.assertEquals("3", fridgeItem2.getOwnerAccount());
    }

    @Test
    @Description("Checks method deleteFridgeItem in FridgeItemServiceImpl class")
    void shouldDeleteFridgeItems() throws ResourceNotFoundException {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        this.FridgeItemServiceImpl.deleteFridgeItem(fridgeItem1.getId());

        List<FridgeItem> fridgeItems = FridgeItemServiceImpl.getFridgeItems();
        Assertions.assertEquals(1, fridgeItems.size());
     }

    @Test
    @Description("Checks method getFridgeItemsByOwnerAccount in FridgeItemServiceImpl class")
    void shouldGetFridgeItemsByOwnerAccount() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> fridgeItems = FridgeItemServiceImpl.getFridgeItems();

        List<FridgeItem> result = FridgeItemServiceImpl.getFridgeItemsByOwnerAccount("2", fridgeItems);
        Assertions.assertEquals(1, result.size());
    }

    protected void assertThatPortIsAvailable(MongoDBContainer container){
         try { //container will start in host and run in the port number, if it is running fine, if not, create exception
             new Socket(container.getHost(), container.getFirstMappedPort());
         } catch (IOException e) {
               throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
         }
     }

}
