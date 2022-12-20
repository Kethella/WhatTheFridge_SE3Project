package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import de.hdm.se3project.backend.services.RecipeService;
import org.springframework.web.bind.annotation.*;
import org.json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes")
    Recipe createRecipe(@RequestBody Recipe newRecipe){
        return recipeService.createRecipe(newRecipe);
    }

    @GetMapping("/recipes/{id}")
    Recipe getOneRecipe(@PathVariable String id) throws ResourceNotFoundException {
        return recipeService.getRecipeById(id);
    }

    @PutMapping("/recipes/{id}")
    Recipe updateRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) throws ResourceNotFoundException {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

    @DeleteMapping("/recipes/{id}")
    void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/recipes/oa={ownerAccount}/")
    List<Recipe> getRecipes(@PathVariable String ownerAccount,
                            @RequestParam(value = "defaultRecipes", defaultValue = "yes") String defaultRecipes,
                            @RequestParam(required = false, value = "category") String category,
                            @RequestParam(required = false, value = "ingredientNames") String ingredientNames,
                            @RequestParam(required = false, value = "tags") String tags) throws ResourceNotFoundException {

        return recipeService.getRecipes(ownerAccount, defaultRecipes, category, ingredientNames, tags);
    }

    @GetMapping("/recipes/tags/oa={ownerAccount}/")
    List<String> getAllRecipeTags(@PathVariable String ownerAccount) throws ResourceNotFoundException {

        return recipeService.getAllRecipeTags(ownerAccount);
    }

    @GetMapping("/categories")
    String getAllSecurityQuestions() {
        JSONArray array = new JSONArray();

        for(Category c: Category.values()) {

            HashMap<String, String> seqQuestion = new HashMap<String, String>();
            seqQuestion.put("enumValue", c.toString());
            seqQuestion.put("text", c.getText());
            JSONObject seqQuestionObject = new JSONObject(seqQuestion);
            array.put(seqQuestionObject);
        }

        return array.toString();
    }



}




