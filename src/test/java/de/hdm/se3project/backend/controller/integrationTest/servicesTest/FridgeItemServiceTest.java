package de.hdm.se3project.backend.controller.integrationTest.servicesTest;


import de.hdm.se3project.backend.controller.integrationTest.AbstractIntegrationTest;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FridgeItemServiceTest extends AbstractIntegrationTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;
    private FridgeItemServiceImpl fridgeItemServiceImpl;

    @BeforeEach
    void setUp(){
        this.fridgeItemServiceImpl = new FridgeItemServiceImpl(fridgeItemRepository);
    }

    @AfterEach
    void cleanUp(){
        this.fridgeItemRepository.deleteAll();
    }

    @Test
    @Description("Checks method getFridgeItems in FridgeItemServiceImpl class")
    void shouldGetFridgeItems() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> result = fridgeItemServiceImpl.getFridgeItems();
        Assertions.assertEquals(2, result.size());
    }


    /**
    @Test
    void shouldCreateFridgeItem(){
        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("chocolate milk");
        fridgeItem.setAmount(1);
        fridgeItem.setExpirationDate("19.01.2023");
        fridgeItem.setOwnerAccount("3");

        FridgeItem fridgeItem1 = this.fridgeItemRepository.save(fridgeItem); //not saving in the actual Database, because container is being used
        Assertions.assertEquals("apple", fridgeItem1.getName());
    }

    */

    /**
     * @Override
     *     public FridgeItem createFridgeItem(FridgeItem item) {
     *         item.setId(IdGenerationService.generateId(item));
     *         return fridgeItemRepository.save(item);
     *     }
     */

}
