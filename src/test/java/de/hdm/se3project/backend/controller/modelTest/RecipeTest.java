package de.hdm.se3project.backend.controller.modelTest;

import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Description("Testing model class: Recipe")
public class RecipeTest {

    Recipe recipe;

    String[] TagArr = {"tag 01"};
    String[] ingArr = {"ingredients 01", "ingredients 02"};
    String[] measArr = {"measures 01", "measures 02"};

    Recipe RECIPE_1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
            Category.MAINCOURSE, TagArr, "image", "link", ingArr, measArr, "1");

    @BeforeEach
    void setUp(){
        recipe = new Recipe();
    }

    @Test
    void recipeTest (){
        recipe = RECIPE_1;
        assertEquals("1", recipe.getId());
        assertEquals("Pasta", recipe.getName());
        assertEquals("instructions - how to make pasta", recipe.getInstructions());
        assertEquals(Category.MAINCOURSE, recipe.getCategory());
        assertArrayEquals(TagArr, recipe.getTags());
        assertArrayEquals(ingArr, recipe.getIngredientNames());
        assertArrayEquals(measArr, recipe.getIngredientMeasures());
        assertEquals("1", recipe.getOwnerAccount());
    }

    @Test
    void recipeNotNullTest() {
        recipe = RECIPE_1;
        assertNotNull(recipe.getId());
        assertNotNull(recipe.getName());
        assertNotNull(recipe.getInstructions());
        assertNotNull(recipe.getCategory());
        assertNotNull(recipe.getTags());
        assertNotNull(recipe.getIngredientNames());
        assertNotNull(recipe.getIngredientMeasures());
        assertNotNull(recipe.getOwnerAccount());
    }
}
