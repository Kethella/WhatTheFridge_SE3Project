package de.hdm.se3project.backend.controller.integrationTest.servicesTest;


import de.hdm.se3project.backend.controller.integrationTest.AbstractIntegrationTest;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import jdk.jfr.Description;
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

}
