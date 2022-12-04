package de.hdm.se3project.backend.controller.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controller.RecipeController;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.services.RecipeService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Description("Testing class: RecipeController")
public class RecipeControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    Recipe RECIPE_1 = new Recipe("1", "Pasta", "instructions - how to make pasta", null, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01", "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");
    Recipe RECIPE_2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake", null, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01", "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "2");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    @Description("Testing method: getAllRecipes")
    void getAllRecipesTest() throws Exception{
        /**
        List<Recipe> recipeList = new ArrayList<>(Arrays.asList(RECIPE_1, RECIPE_2));

        Mockito.when(recipeService.getRecipes("1", null, null, "ingredients 01", "tag 01" )).thenReturn(recipeList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1//recipes") //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isOk()) //when we do a get request to get all the records, we get a 200 status = ok
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Chocolate Cake")));

         **/
    }
}
