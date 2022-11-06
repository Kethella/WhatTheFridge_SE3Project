package de.hdm.se3project.backend.repository;

import de.hdm.se3project.backend.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {


}
