package de.hdm.se3project.backend.jUnitTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controllers.RecipeController;
import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Recipe;
import de.hdm.se3project.backend.models.enums.Category;
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
import static org.mockito.Mockito.times;
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

    Recipe RECIPE_1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
            Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
            "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

    Recipe RECIPE_2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
            Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
            "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    @Description("Testing method: getRecipes - Should get all the recipes - GET request")
    void getRecipesTest() throws Exception {

        List<Recipe> recipeList = new ArrayList<>(Arrays.asList(RECIPE_1, RECIPE_2));

        //PathVariable notation (Owner account) is the only mandatory field, the RequestParam notation
        //is not mandatory but necessary to create the tests to check if the parameters works.
        Mockito.when(recipeService.getRecipes("1", "no", null, "ingredients 01",
                "tag 01" )).thenReturn(recipeList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/oa=1/")
                        .param("defaultRecipes", "no")
                        .param("ingredientNames", "ingredients 01")
                        .param("tags", "tag 01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    @Description("Testing model method: createRecipe - Should create a new recipe - POST request")
    void createRecipeTest() throws Exception {

        Recipe recipe = new Recipe();

        recipe.setName("Vegan Lasagna");
        recipe.setInstructions("instructions sample");
        recipe.setTags(new String[]{"tag x"});
        recipe.setImage(recipe.getImage());
        recipe.setLink("LinkHere");
        recipe.setCategory(Category.MAINCOURSE);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Vegan Lasagna")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerAccount", is("2")));
    }

    @Test
    @Description("Testing model method: getOneRecipe - Should return one recipe based on its id - GET request")
    void getOneRecipeTest() throws Exception {

        Mockito.when(recipeService.getRecipeById("2")).thenReturn(RECIPE_2);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Chocolate Cake")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", is("link")));
    }

    @Test
    @Description("Testing method: getOneRecipe Exception - Should throw an exception when ID not found - GET Request")
    void getOneFridgeItem_notfound_Test() throws Exception {

        String idItem = "10";

        Mockito.when(recipeService.getRecipeById(idItem)).thenThrow(new ResourceNotFoundException(
                "Item not found for this id :: " + idItem));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/{id}", idItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Description("Testing method: updateRecipe - Should change the name of the recipe - PUT request")
    void updateRecipe() throws Exception {

        Recipe recipeUpdated = RECIPE_2;

        recipeUpdated.setName("Chocolate cake with cream");
        recipeUpdated.setCategory(RECIPE_2.getCategory());
        recipeUpdated.setInstructions(RECIPE_2.getInstructions());
        recipeUpdated.setImage(RECIPE_2.getImage());
        recipeUpdated.setTags(RECIPE_2.getTags());
        recipeUpdated.setLink(RECIPE_2.getLink());

        Mockito.when(recipeService.updateRecipe("2", recipeUpdated)).thenReturn(recipeUpdated);

        String updatedContent = objectWriter.writeValueAsString(recipeUpdated);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/recipes/{id}",
                        RECIPE_2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Chocolate cake with cream")));
    }

    @Test
    @Description("Testing method:  deleteRecipe - Should delete an recipe based on its id - DELETE request")
    void deleteRecipeTest() throws Exception{

        String itemId = "1";

        Mockito.when(recipeService.getRecipeById(itemId)).thenReturn(RECIPE_1);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recipes/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Description("It checks if the deleteById method on RecipeServiceImpl mock object was called with the same id")
    public void testDelete() {
        String id = "1";
        // Act
        recipeService.deleteRecipe(id);
        // Assert
        Mockito.verify(recipeService).deleteRecipe(id);
        Mockito.verify(recipeService, times(1)).deleteRecipe(id);
    }

}
