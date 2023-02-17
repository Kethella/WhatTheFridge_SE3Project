package de.hdm.se3project.backend.controllers;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Recipe;
import de.hdm.se3project.backend.models.enums.Category;
import de.hdm.se3project.backend.services.RecipeService;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    Recipe createRecipe(@RequestBody Recipe newRecipe){
        return recipeService.createRecipe(newRecipe);
    }

    @GetMapping("/{id}")
    Recipe getOneRecipe(@PathVariable String id) throws ResourceNotFoundException {
        return recipeService.getRecipeById(id);
    }

    @PutMapping("/{id}")
    Recipe updateRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) throws ResourceNotFoundException {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

    @DeleteMapping("/{id}")
    void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/oa={ownerAccount}/")
    List<Recipe> getRecipes(@PathVariable String ownerAccount,
                            @RequestParam(value = "defaultRecipes", defaultValue = "yes") String defaultRecipes,
                            @RequestParam(required = false, value = "category") String category,
                            @RequestParam(required = false, value = "ingredientNames") String ingredientNames,
                            @RequestParam(required = false, value = "tags") String tags) throws ResourceNotFoundException {

        return recipeService.getRecipes(ownerAccount, defaultRecipes, category, ingredientNames, tags);
    }


    @GetMapping("/tags/oa={ownerAccount}")
    List<String> getAllRecipeTags(@PathVariable String ownerAccount) throws ResourceNotFoundException {

        return recipeService.getAllRecipeTags(ownerAccount);
    }

    @GetMapping("/categories")
    String getAllCategories() {
        return recipeService.getAllCategories();
    }

}






