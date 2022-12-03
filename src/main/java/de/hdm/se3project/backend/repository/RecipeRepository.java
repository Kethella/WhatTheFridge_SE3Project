package de.hdm.se3project.backend.repository;

import de.hdm.se3project.backend.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {




}
