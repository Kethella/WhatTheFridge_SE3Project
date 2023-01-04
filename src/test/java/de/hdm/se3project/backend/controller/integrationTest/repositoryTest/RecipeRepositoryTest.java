package de.hdm.se3project.backend.controller.integrationTest.repositoryTest;

import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import de.hdm.se3project.backend.repository.RecipeRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository; //getting instance of the Recipe Repository

    @AfterEach
    void cleanUp(){
        this.recipeRepository.deleteAll();
    }

    @Test
    @Description("Verifies if container is empty before save recipe")
    void listOfItemsShouldBeEmpty(){
        List<Recipe> recipes = recipeRepository.findAll();
        Assertions.assertEquals(0, recipes.size());
    }

    @Test
    @Description("Save one Recipe in the Database")
    void shouldSaveRecipe(){
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

        Recipe recipe1 = this.recipeRepository.save(recipe); //not saving in the actual Database, because container is being used
        Assertions.assertEquals("Vegan Lasagna", recipe1.getName());
    }

    @Test
    @Description("Check if the Recipe List has the size accordingly to the amount of saved recipes")
    void shouldCheckListSizeOfRecipe() {
        Recipe recipe1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
                Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipe2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);

        List<Recipe> recipes = recipeRepository.findAll();
        Assertions.assertEquals(2, recipes.size());
    }

    @Test
    @Description("Check if the List of Recipe has the size accordingly to the amount of saved minus deleted recipe")
    void shouldDeleteRecipe() {
        Recipe recipe1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
                Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipe2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);

        this.recipeRepository.delete(recipe2);

        List<Recipe> recipes = recipeRepository.findAll();
        Assertions.assertEquals(1, recipes.size());
    }

}
