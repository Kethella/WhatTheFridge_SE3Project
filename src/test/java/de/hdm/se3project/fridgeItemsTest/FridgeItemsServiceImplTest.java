package de.hdm.se3project.fridgeItemsTest;

import de.hdm.se3project.ConfigurationTest;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = FridgeItemServiceImpl.class)
@DisplayName("FridgeItemsServiceImplTest")
public class FridgeItemsServiceImplTest extends ConfigurationTest {

    @MockBean
    private FridgeItemRepository fridgeItemRepository;

    @Autowired
    private FridgeItemServiceImpl fridgeItemServiceImpl;


    //Just to check if works
    @Test
    public void test01() {
        System.out.println("blabla");
    }



}
