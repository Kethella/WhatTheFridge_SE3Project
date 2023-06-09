package de.hdm.se3project.backend.jUnitTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controllers.FridgeItemController;
import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Description("Testing class: FridgeItemController")
class FridgeItemControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private FridgeItemService fridgeItemService;

    @InjectMocks
    private FridgeItemController fridgeItemController;

    FridgeItem FRIDGE_ITEM_1 = new FridgeItem("1", "tomato", 5, "22.01.2023", "1");
    FridgeItem FRIDGE_ITEM_2 = new FridgeItem("2", "lemon", 2, "26.01.2023", "1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fridgeItemController).build();
    }

    @Test
    @Description("Testing method: getFridgeItems - Should return all the items saved in an account - GET request")
    void getFridgeItemsTest() throws Exception {
        List<FridgeItem> fridgeItems = new ArrayList<>(Arrays.asList(FRIDGE_ITEM_1, FRIDGE_ITEM_2));

        Mockito.when(fridgeItemService.getFridgeItems("1")).thenReturn(fridgeItems);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/oa=1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("lemon")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", is(5)));
    }

    @Test
    @Description("Testing method: getOneFridgeItem - Should get one item according to its ID - GET request")
    void getOneFridgeItemTest() throws Exception {

        String itemId = "1";

        Mockito.when(fridgeItemService.getFridgeItemById(itemId)).thenReturn(FRIDGE_ITEM_1);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", is(5)));
    }

    @Test
    @Description("Testing method: getFridgeItemById Exception - Should throw an exception when ID not found - GET request")
    void getOneFridgeItem_notfound_Test() throws Exception {

        String idItem = "5";

        Mockito.when(fridgeItemService.getFridgeItemById(idItem)).thenThrow(new ResourceNotFoundException(
                "Item not found for this id :: " + idItem));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/{id}", idItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Description("Testing method: createFridgeItem - Should create a new item - POST request")
    public void createFridgeItemTest() throws Exception {

        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("milk");
        fridgeItem.setAmount(2);
        fridgeItem.setExpirationDate("29.01.2023");
        fridgeItem.setOwnerAccount("1");

        Mockito.when(fridgeItemService.createFridgeItem(fridgeItem)).thenReturn(fridgeItem);

        String contentStr = objectWriter.writeValueAsString(fridgeItem);

        MockHttpServletRequestBuilder mockRequest
                = MockMvcRequestBuilders.post("http://localhost:4200/api/v1/fridgeItems")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contentStr);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("milk")));
    }

    @Test
    @Description("Testing method: updateFridgeItem - Should change the amount and expiration date of the item - PUT request")
    void updateFridgeItemTest() throws Exception {

        FridgeItem fridgeItemUpdated = FRIDGE_ITEM_1;
        fridgeItemUpdated.setAmount(1);
        fridgeItemUpdated.setExpirationDate("15.09.2023");

        Mockito.when(fridgeItemService.updateFridgeItem("1", fridgeItemUpdated)).thenReturn(fridgeItemUpdated);

        String updatedContent = objectWriter.writeValueAsString(fridgeItemUpdated);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/fridgeItems/{id}",
                        FRIDGE_ITEM_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", is(1)));
    }

    @Test
    @Description("Testing method:  deleteFridgeItem - Should delete an item based on its id - DELETE request")
    void deleteFridgeItemTest() throws Exception {

        String itemId = "2";
        Mockito.when(fridgeItemService.getFridgeItemById(itemId)).thenReturn(FRIDGE_ITEM_2);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/fridgeItems/{id}", FRIDGE_ITEM_2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Description("It checks if the deleteById method on FridgeItemServiceImpl mock object was called with the same id")
    public void testDelete() throws ResourceNotFoundException {
        String id = "1";
        // Act
        fridgeItemService.deleteFridgeItem(id);
        // Assert
        Mockito.verify(fridgeItemService).deleteFridgeItem(id);
        Mockito.verify(fridgeItemService, times(1)).deleteFridgeItem(id);
    }
}
