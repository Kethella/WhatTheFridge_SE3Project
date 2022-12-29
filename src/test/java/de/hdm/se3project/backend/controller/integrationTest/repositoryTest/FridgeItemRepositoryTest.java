package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.controller.integrationTest.AbstractIntegrationTest;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.MongoDBContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class FridgeItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;  //getting instance of the Fridge Item Repository

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
