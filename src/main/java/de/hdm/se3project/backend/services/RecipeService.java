package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Recipe;

import java.util.List;

public interface RecipeService {
    public abstract Recipe createRecipe(Recipe product);
    public abstract Recipe updateRecipe(String id, Recipe product) throws ResourceNotFoundException;
    public abstract void deleteRecipe(String id);
    public abstract List<Recipe> getAllRecipes();
    public abstract Recipe getRecipeById(String id) throws ResourceNotFoundException;

    List<Recipe> getRecipesByOwnerAccount(String ownerAccount, List<Recipe> inputRecipes);

    List<Recipe> getRecipes(String id, String defaultRecipes, String categories, String ingredientNames, String tags) throws ResourceNotFoundException;

}
