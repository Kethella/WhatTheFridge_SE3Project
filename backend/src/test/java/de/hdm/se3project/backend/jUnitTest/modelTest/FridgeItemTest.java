package de.hdm.se3project.backend.jUnitTest.modelTest;

import de.hdm.se3project.backend.models.FridgeItem;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Description("Testing model class: FridgeItem")
public class FridgeItemTest {

    FridgeItem fridgeItem;

    FridgeItem FRIDGE_ITEM_1 = new FridgeItem("1", "bread", 2, "01.01.2023", "1");

    @BeforeEach
    void setUp() {
        fridgeItem = new FridgeItem();
    }

    @Test
    void fridgeItemTest(){
        fridgeItem = FRIDGE_ITEM_1;
        assertEquals("1", fridgeItem.getId());
        assertEquals("bread", fridgeItem.getName());
        assertEquals(2, fridgeItem.getAmount());
        assertEquals("01.01.2023", fridgeItem.getExpirationDate());
        assertEquals("1", fridgeItem.getOwnerAccount());
    }

    @Test
    void setFridgeItemNotNullTest(){
        fridgeItem = FRIDGE_ITEM_1;
        assertNotNull(fridgeItem.getId());
        assertNotNull(fridgeItem.getName());
        assertNotNull(fridgeItem.getExpirationDate());
        assertNotNull(fridgeItem.getOwnerAccount());
    }
}
