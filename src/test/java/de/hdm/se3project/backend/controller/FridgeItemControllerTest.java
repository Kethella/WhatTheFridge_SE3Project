package de.hdm.se3project.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://www.youtube.com/watch?v=KYkEMuA50yE&ab_channel=ProgrammingKnowledge

@RunWith(MockitoJUnitRunner.class)
class FridgeItemControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private FridgeItemService fridgeItemService;

    @InjectMocks
    private FridgeItemController fridgeItemController;

    FridgeItem FRIDGE_ITEM_1 = new FridgeItem("1", "tomato", 5, "12.12.2022", "1");
    FridgeItem FRIDGE_ITEM_2 = new FridgeItem("2", "potato", 6, "13.12.2022", "2");
    FridgeItem FRIDGE_ITEM_3 = new FridgeItem("3", "lemon", 2, "12.01.2023", "1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fridgeItemController).build();
    }

    @Test
    void createFridgeItem() throws Exception {

    }

    @Test
    void getOneFridgeItem() {
    }

    @Test
    void updateFridgeItem() {
    }

    @Test
    void deleteFridgeItem() {
    }

    @Test
    void getFridgeItems() throws Exception {
        List<FridgeItem> fridgeItems = new ArrayList<>(Arrays.asList(FRIDGE_ITEM_1, FRIDGE_ITEM_3));

        Mockito.when(fridgeItemService.getFridgeItems("1")).thenReturn(fridgeItems);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1//fridgeItems/oa=1/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("lemon")));
    }
}