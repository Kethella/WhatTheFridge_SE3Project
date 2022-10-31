package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exception.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import de.hdm.se3project.backend.repository.RecipeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private final RecipeRepository repository;

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipes")
        //@CrossOrigin
    List<Recipe> getAllRecipes(){
        return repository.findAll();
    }

    @GetMapping("/recipes/{id}")
        //@CrossOrigin
    Recipe getOneRecipe(@PathVariable String id) throws ResourceNotFoundException {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }

    //temporary for testing
    @GetMapping("/recipes/category/{id}")
    String getCategoryText(@PathVariable String id) throws ResourceNotFoundException {
        Recipe category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id : " + id));

        return category.getCategory().getText();
    }



    @PostMapping("/recipes")
        //@CrossOrigin
    Recipe createAccount(@RequestBody Recipe newRecipe){ //whatever data you submit prom the client side will be accepted in the post object
        return repository.save(newRecipe);
    }

    @PutMapping("/recipes/{id}")
    Recipe replaceAccount(@PathVariable String id, @RequestBody Recipe newRecipe) throws ResourceNotFoundException {

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        recipe.setId(newRecipe.getId());
        recipe.setName(newRecipe.getName());
        recipe.setInstructions(newRecipe.getInstructions());
        recipe.setTags(newRecipe.getTags());
        recipe.setPicture(newRecipe.getPicture());
        recipe.setCategory(newRecipe.getCategory());

        return repository.save(recipe);
    }

    @DeleteMapping("/recipes/{id}")
    void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }
}




