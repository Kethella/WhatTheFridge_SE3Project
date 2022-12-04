package de.hdm.se3project.backend.controller.modelTest;

import de.hdm.se3project.backend.model.Recipe;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    Recipe recipe;

    Recipe RECIPE_1 = new Recipe("1", "Pasta", "instructions - how to make pasta", null, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01", "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

    @BeforeEach
    void setUp(){
        recipe = new Recipe();
    }

    @Test
    @Description("Testing method: Recipe")
    void recipeTest (){
        recipe = RECIPE_1;
        assertEquals("1", recipe.getId());
        assertEquals("Pasta", recipe.getName());
    }

}
