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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Description("Testing class: RecipeController")
class RecipeControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    Recipe RECIPE_1 = new Recipe("1", "Pasta", "instructions - how to make pasta", null, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01", "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");
    Recipe RECIPE_2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake", null, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01", "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }
    
    @Test
    @Description("Testing method: createRecipe - Should create a new recipe")
    void createRecipeTest() throws Exception {

        Recipe recipe = new Recipe();

        recipe.setName("Vegan Lasagna");
        recipe.setInstructions("instructions sample");
        recipe.setTags(new String[]{"tag x"});
        recipe.setImage(recipe.getImage());
        recipe.setLink("LinkHere");
        recipe.setCategory(null);
        recipe.setIngredientNames(new String[]{"ingredients 01", "ingredients 02"});
        recipe.setIngredientMeasures(new String[]{"measures 01", "measures 02"});
        recipe.setOwnerAccount("2");

        Mockito.when(recipeService.createRecipe(recipe)).thenReturn(recipe);

        String contentStr = objectWriter.writeValueAsString(recipe);

        MockHttpServletRequestBuilder mockRequest
                = MockMvcRequestBuilders.post("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contentStr);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Vegan Lasagna")));
    }

    @Test
    @Description("Testing method: getOneRecipe - Should return one recipe based on its id")
    void getOneRecipeTest() throws Exception {

        Mockito.when(recipeService.getRecipeById("2")).thenReturn(RECIPE_2);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/{id}", "2") //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isOk()) //when we do a get request to get all the records, we get a 200 status = ok
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue())) //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Chocolate Cake")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", is("link")));
    }

    @Test
    void updateRecipe() throws Exception {
        //Test here
    }

    @Test
    @Description("Testing method:  deleteRecipe - should delete an recipe based on its id")
    void deleteRecipeTest() throws Exception{
        String itemId = "1";

        Mockito.when(recipeService.getRecipeById(itemId)).thenReturn(RECIPE_1);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recipes/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //TODO: Test is complicate because of matching of parameters
    //Should maybe the getRecipes method just need the account owner as parameter?
    @Test
    @Description("Testing method: getRecipes")
    void getRecipesTest() throws Exception {

        List<Recipe> recipeList = new ArrayList<>(Arrays.asList(RECIPE_1, RECIPE_2));

        Mockito.when(recipeService.getRecipes("1", null, null, "ingredients 01", "tag 01" )).thenReturn(recipeList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/oa={ownerAccount}/", "1") //ending point from where we get out data
                        .contentType(MediaType.APPLICATION_JSON)) //we want the file to be a JSON
                .andExpect(status().isOk()) //when we do a get request to get all the records, we get a 200 status = ok
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))); //indicates the size of the return, in this case there is 2 arrays, so the size is = 2
                //.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Chocolate Cake")));
    }
}
