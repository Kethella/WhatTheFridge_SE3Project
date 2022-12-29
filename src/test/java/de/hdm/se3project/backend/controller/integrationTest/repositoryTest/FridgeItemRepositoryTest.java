package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.controller.integrationTest.AbstractIntegrationTest;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.MongoDBContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.Socket;

public class FridgeItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;  //getting instance of the Fridge Item Repository

    @Test
    @Description("Check if Container has started and the public port is available")
    void containerStartsAndPublicPortIsAvailable() {
        assertThatPortIsAvailable(container);
    }

    //TODO: verify if container is empty before save item

    @Test
    @Description("Save one Fridge Item in the Database")
    void saveFridgeItem(){
        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("apple");
        fridgeItem.setAmount(2);
        fridgeItem.setExpirationDate("13.01.2023");
        fridgeItem.setOwnerAccount("1");

        FridgeItem fridgeItem1 = fridgeItemRepository.save(fridgeItem); //not saving in the actual Database,
        // because container is being used
        //assert fridgeItem1 != null;
        Assertions.assertEquals("apple", fridgeItem1.getName());
    }


    //TODO: verify 200 - OK
    //TODO: verify 404 - ERROR

    //TODO: verify if FI was saved
    //TODO: Verify if item was updated *maybe

    //TODO: delete item

    private void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in host and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getHost(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }

}
