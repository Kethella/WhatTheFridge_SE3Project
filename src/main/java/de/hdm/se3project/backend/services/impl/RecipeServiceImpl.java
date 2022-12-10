package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import de.hdm.se3project.backend.repository.RecipeRepository;
import de.hdm.se3project.backend.services.IdGenerationService;
import de.hdm.se3project.backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        recipe.setId(IdGenerationService.generateId(recipe));
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(String id) throws ResourceNotFoundException {

        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }

    @Override
    public Recipe updateRecipe(String id, Recipe newRecipe) throws ResourceNotFoundException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        if(newRecipe.getName() != null){
            recipe.setName(newRecipe.getName());
        }
        if(newRecipe.getCategory() != null){
            recipe.setCategory(newRecipe.getCategory());
        }
        if(newRecipe.getInstructions() != null){
            recipe.setInstructions(newRecipe.getInstructions());
        }
        if(newRecipe.getImage() != null){
            recipe.setImage(newRecipe.getImage());
        }
        if(newRecipe.getTags() != null){
            recipe.setTags(newRecipe.getTags());
        }
        if(newRecipe.getLink() != null){
            recipe.setLink(newRecipe.getLink());
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> getRecipes(String ownerAccount,String defaultRecipes,String category, String ingredientNames, String tags) throws ResourceNotFoundException {
        List<Recipe> recipes = getAllRecipes();

        recipes = getRecipesByOwnerAccount(ownerAccount, recipes);

        if(defaultRecipes.equals("yes")){
            List<Recipe> externalRecipes = getRecipesByOwnerAccount(null, getAllRecipes());
            recipes.addAll(externalRecipes);
        }

        if(category != null){
            recipes = getRecipesWithCategory(category, recipes);
        }
        if(ingredientNames != null){
            recipes = getRecipesWithMultipleIngredientNames(ingredientNames, recipes);
        }
        if(tags != null){
            recipes = getRecipesWithMultipleTags(tags, recipes);
        }

        return recipes;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getRecipesByOwnerAccount(String ownerAccount, List<Recipe> inputRecipes) {
        List<Recipe> result = new ArrayList<>();

        for (Recipe recipe: inputRecipes){
            if(recipe.getOwnerAccount() == null){
                if(ownerAccount == null){
                    result.add(recipe);
                }
            }
            else if (recipe.getOwnerAccount().equals(ownerAccount)){
                result.add(recipe);
            }
        }

        if(result.isEmpty()){
            return null;
        }
        return result;
    }

    public List<Recipe> getRecipesWithCategory(String inputCategory, List<Recipe> inputRecipes) {

        List<Recipe> resultRecipes = new ArrayList<>();
        for(Recipe recipe: inputRecipes){
            if (Category.valueOf(inputCategory).equals(recipe.getCategory())){
                resultRecipes.add(recipe);
            }
        }

        return resultRecipes;
    }

    //ingredientName AND ingredientName
    public List<Recipe> getRecipesWithMultipleIngredientNames(String inputIngredientNames, List<Recipe> inputRecipes) {

        //SPLIT THE INGREDIENTS
        List<String> ingredientNames = new ArrayList<>();
        while (inputIngredientNames.contains(",")){
            ingredientNames.add(inputIngredientNames.substring(0, inputIngredientNames.indexOf(",")));
            inputIngredientNames = inputIngredientNames.substring(inputIngredientNames.indexOf(",")+1);
        }
        ingredientNames.add(inputIngredientNames);


        //FILTER THE GIVEN RECIPES BY MUST HAVE INGREDIENTS
        List<Recipe> resultRecipes = new ArrayList<>();
        for(Recipe recipe: inputRecipes){

            boolean allMustIngredientAreIncluded = true;
            for(String mustIngredient : ingredientNames){

                boolean mustIngredientIsIncluded = false;
                for(String recipeIngredient: recipe.getIngredientNames()){

                    if (mustIngredient.equals(recipeIngredient)){
                        mustIngredientIsIncluded = true;
                        break;
                    }
                }

                if(!mustIngredientIsIncluded){
                    allMustIngredientAreIncluded = false;
                    break;
                }
            }

            if(allMustIngredientAreIncluded){
                resultRecipes.add(recipe);
            }
        }

        return resultRecipes;
    }


    //tag AND tag
    public List<Recipe> getRecipesWithMultipleTags(String inputTags, List<Recipe> inputRecipes) {
        //SPLIT THE TAGS
        List<String> tags = new ArrayList<>();
        while (inputTags.contains(",")){
            tags.add(inputTags.substring(0, inputTags.indexOf(",")));
            inputTags = inputTags.substring(inputTags.indexOf(",")+1);
        }
        tags.add(inputTags);


        //FILTER THE GIVEN RECIPES BY MUST HAVE Tags
        List<Recipe> resultRecipes = new ArrayList<>();
        for(Recipe recipe: inputRecipes){

            if(recipe.getTags() != null){
                boolean allMustTagsAreIncluded = true;
                for(String mustTag : tags){

                    boolean mustTagIsIncluded = false;
                    for(String recipeTag: recipe.getTags()){ //null

                        if (mustTag.equals(recipeTag)){
                            mustTagIsIncluded = true;
                            break;
                        }
                    }

                    if(!mustTagIsIncluded){
                        allMustTagsAreIncluded = false;
                        break;
                    }
                }

                if(allMustTagsAreIncluded){
                    resultRecipes.add(recipe);
                }
            }
        }

        return resultRecipes;
    }






    //category OR category
    public List<Recipe> getRecipesWithMultipleCategories(String inputCategories, List<Recipe> inputRecipes) {
        //SPLIT THE TAGS
        List<String> categories = new ArrayList<>();
        while (inputCategories.contains(",")){
            categories.add(inputCategories.substring(0, inputCategories.indexOf(",")));
            inputCategories = inputCategories.substring(inputCategories.indexOf(",")+1);
        }
        categories.add(inputCategories);


        //FILTER THE GIVEN RECIPES BY MUST HAVE ONE OF THE CATEGORIES
        List<Recipe> resultRecipes = new ArrayList<>();
        for(Recipe recipe: inputRecipes){

            for(String oneMustCategory : categories){

                if (Category.valueOf(oneMustCategory).equals(recipe.getCategory())){
                    resultRecipes.add(recipe);
                    break;
                }
            }
        }

        return resultRecipes;
    }
}
