package de.hdm.se3project.fridgeItemTests;

import de.hdm.se3project.ConfigurationTest;
import de.hdm.se3project.backend.repository.ItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = FridgeItemServiceImpl.class)
@DisplayName("FridgeItemsServiceImplTest")
public class FridgeItemsServiceImplTest extends ConfigurationTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private FridgeItemServiceImpl fridgeItemServiceImpl;

    //Just to check if works
    @Test
    @DisplayName("test01 - example")
    public void test01() {
        System.out.println("blabla");
    }

    /**
    //TODO: test with Lists
    @Test
    @DisplayName("Should return a list of fridge Items")
    public void fridgeItemsList() {

        Mockito.when(itemRepository.findAll()).thenReturn(null);  //The problem is initially here

        fridgeItemServiceImpl.getFridgeItems();
    }
    **/
}

