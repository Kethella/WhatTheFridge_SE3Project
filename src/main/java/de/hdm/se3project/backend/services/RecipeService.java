package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe createRecipe(Recipe product);
    Recipe updateRecipe(String id, Recipe product) throws ResourceNotFoundException;
    void deleteRecipe(String id);
    Recipe getRecipeById(String id) throws ResourceNotFoundException;
    List<Recipe> getRecipes(String id, String defaultRecipes, String categories, String ingredientNames, String tags) throws ResourceNotFoundException;
    List<String> getAllRecipeTags(String ownerId);

}
