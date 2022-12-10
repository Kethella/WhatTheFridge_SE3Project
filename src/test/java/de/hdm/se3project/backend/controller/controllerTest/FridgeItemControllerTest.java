package de.hdm.se3project.backend.controller.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controller.FridgeItemController;
import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: create a base test class

@RunWith(MockitoJUnitRunner.class)  //This notation ensure we are just using mockito in our class, nothing else
@Description("Testing class: FridgeItemController")
class FridgeItemControllerTest {

    //MOCKITO
    //1° we mark dependencies for the class under test --> so we will be mocking the fridgeItem service (the methods with
    // its functionalities are there, therefore we mock the service class)
    //2° Execute code in the class under test
    //3° Validate if the code executed as expected

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private FridgeItemService fridgeItemService;

    @InjectMocks
    private FridgeItemController fridgeItemController;

    //test data
    FridgeItem FRIDGE_ITEM_1 = new FridgeItem("1", "tomato", 5, "22.01.2023", "1");
    FridgeItem FRIDGE_ITEM_2 = new FridgeItem("2", "potato", 6, "23.01.2023", "2");
    FridgeItem FRIDGE_ITEM_3 = new FridgeItem("3", "lemon", 2, "26.01.2023", "1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fridgeItemController).build();  //ensures that the test will be on the FI repository class
    }

    @Test
    @Description("Testing method: getFridgeItems - should return all the items saved in an account")
    void getFridgeItemsTest() throws Exception {
        List<FridgeItem> fridgeItems = new ArrayList<>(Arrays.asList(FRIDGE_ITEM_1, FRIDGE_ITEM_3));

        Mockito.when(fridgeItemService.getFridgeItems("1")).thenReturn(fridgeItems);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/oa=1/") //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isOk()) //when we do a get request to get all the records, we get a 200 status = ok
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("lemon")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", is(5)));
    }

    @Test
    @Description("Testing method: getOneFridgeItem - should get one item according to its ids")
    void getOneFridgeItemTest() throws Exception {

        String itemId = "1";

        Mockito.when(fridgeItemService.getFridgeItemById(itemId)).thenReturn(FRIDGE_ITEM_1);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/{id}", itemId) //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isOk()) //when we do a get request to get all the records, we get a 200 status = ok
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue())) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", is(5)));
    }

    @Test
    @Description("Testing method: getFridgeItemById Exception - Should throw an exception when ID not found")
    void getOneFridgeItem_notfound_Test() throws Exception {

        String idItem = "5";

        Mockito.when(fridgeItemService.getFridgeItemById(idItem)).thenThrow(new ResourceNotFoundException("Item not found for this id :: " + idItem));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/fridgeItems/{id}", idItem) //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isNotFound());
    }

    @Test
    @Description("Testing method: createFridgeItem - should create a new item")
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
                        .jsonPath("$", notNullValue())) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("milk")));
    }

    //TODO: test not working - 415 error
    @Test
    @Description("Testing method: updateFridgeItem - should change the amount and expiration date of the item")
    void updateFridgeItemTest() throws Exception {
        FRIDGE_ITEM_1.setId("1");

        FridgeItem fridgeItemUpdated = new FridgeItem();

        fridgeItemUpdated.setId(FRIDGE_ITEM_1.getId());
        fridgeItemUpdated.setName(FRIDGE_ITEM_1.getName());
        fridgeItemUpdated.setAmount(1); //need to mock the number 1
        fridgeItemUpdated.setExpirationDate("15.09.2023"); //need to mock the  exp date
        fridgeItemUpdated.setOwnerAccount(FRIDGE_ITEM_1.getOwnerAccount());

        Mockito.when(fridgeItemService.getFridgeItemById(FRIDGE_ITEM_1.getId())).thenReturn(FRIDGE_ITEM_1);
        Mockito.when(fridgeItemService.updateFridgeItem(fridgeItemUpdated)).thenReturn(fridgeItemUpdated);

        String updatedContent = objectWriter.writeValueAsString(fridgeItemUpdated);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/fridgeItems/{id}", FRIDGE_ITEM_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", notNullValue())) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("tomato")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", is(1)));
    }

    @Test
    @Description("Testing method:  deleteFridgeItem - should delete an item based on its id")
    void deleteFridgeItemTest() throws Exception {

        String itemId = "3";

        Mockito.when(fridgeItemService.getFridgeItemById(itemId)).thenReturn(FRIDGE_ITEM_3);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/fridgeItems/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
