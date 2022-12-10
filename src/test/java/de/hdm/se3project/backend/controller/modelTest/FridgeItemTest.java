package de.hdm.se3project.backend.controller.modelTest;

import de.hdm.se3project.backend.model.FridgeItem;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Description("Testing class: FridgeItem")
public class FridgeItemTest {

    FridgeItem fridgeItem;

    FridgeItem FRIDGE_ITEM_1 = new FridgeItem("1", "tomato", 5, "22.01.2023", "1");

    @BeforeEach
    void setUp() {
        fridgeItem = new FridgeItem();
    }

    @Test
    @Description("Testing method: FridgeItem")
    void fridgeItemTest(){
        fridgeItem = FRIDGE_ITEM_1;
        assertEquals("1", fridgeItem.getId());
        assertEquals("tomato", fridgeItem.getName());
        assertEquals(5, fridgeItem.getAmount());
        assertEquals("22.01.2023", fridgeItem.getExpirationDate());
        assertEquals("1", fridgeItem.getOwnerAccount());
    }
}
