package de.hdm.se3project.serviceTest;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

//@AutoConfigureMockMvc
@SpringBootTest (classes = FridgeItemServiceImpl.class)
//@SpringBootApplication
@DisplayName("Class: FridgeItemsServiceImplTest")
public class FridgeItemsServiceImplTest {

    FridgeItem originalItem = new FridgeItem("01", "cheese", 2, "10-02-2023", "03");

    @Test
    @DisplayName("Testing method: updateFridgeItemTest")
    public void updateFridgeItemTest() throws ResourceNotFoundException {
        FridgeItemRepository fridgeItemRepositoryMock = mock(FridgeItemRepository.class);

        FridgeItem updatedItem = new FridgeItem("01", "cheese", 1, "10-02-2023", "03");

        when(fridgeItemRepositoryMock.findById(originalItem.getId())).thenReturn(Optional.of(updatedItem));

        FridgeItemServiceImpl fridgeItemServiceImpl = new FridgeItemServiceImpl(fridgeItemRepositoryMock);

        String result = fridgeItemServiceImpl.updateFridgeItem(originalItem.getId(), updatedItem).getId();
        assertEquals("01", result);
    }

}