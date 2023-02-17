package de.hdm.se3project.backend.integrationTest.servicesTest;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.FridgeItem;
import de.hdm.se3project.backend.repositories.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class FridgeItemServiceImplTest {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;

    private FridgeItemServiceImpl fridgeItemService;

    @BeforeEach
    void setUp(){
        this.fridgeItemService = new FridgeItemServiceImpl(fridgeItemRepository);
    }

    @AfterEach
    void cleanUp(){
        this.fridgeItemRepository.deleteAll();
    }

    @Test
    @Description("Checks method getFridgeItems in FridgeItemServiceImpl class")
    void shouldGetFridgeItems() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "1");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> result = fridgeItemService.getFridgeItems("1");
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

        FridgeItem fridgeItem2 = this.fridgeItemService.updateFridgeItem(fridgeItem.getId(), fridgeItem1);
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

        this.fridgeItemService.deleteFridgeItem(fridgeItem1.getId());

        List<FridgeItem> fridgeItems = fridgeItemService.getFridgeItems("2");
        Assertions.assertEquals(1, fridgeItems.size());
     }

    @Test
    @Description("Checks method getFridgeItemsByOwnerAccount in FridgeItemServiceImpl class")
    void shouldGetFridgeItemsByOwnerAccount() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> fridgeItems = fridgeItemService.getFridgeItems("2");

        List<FridgeItem> result = fridgeItemService.getFridgeItemsByOwnerAccount("2", fridgeItems);
        Assertions.assertEquals(1, result.size());
    }

}
