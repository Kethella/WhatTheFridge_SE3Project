package de.hdm.se3project.backend.model;

import de.hdm.se3project.backend.model.enums.Category;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;


@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;
    private String name;
    private Category category;
    private String instructions;
    private String picture;
    private String[] tags;
    private String link;
    private String[] ingredientNames;
    private String[] ingredientMeasures;
    private String ownerAccount;

    public Recipe () {

    }

    public Recipe(String id, String name, String instructions, Category category, String[] tags, String picture, String link, String[] ingredientNames, String[] ingredientMeasures, String ownerAccount) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.tags = tags;
        this.picture = picture;
        this.link = link;
        this.category = category;
        this.ingredientNames = ingredientNames;
        this.ingredientMeasures = ingredientMeasures;
        this.ownerAccount = ownerAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String[] getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(String[] ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public String[] getIngredientMeasures() {
        return ingredientMeasures;
    }

    public void setIngredientMeasures(String[] ingredientMeasures) {
        this.ingredientMeasures = ingredientMeasures;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }
/*
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        // Check if obj is an instance of Recipe or not
        if (!(obj instanceof Recipe recipeObj)) {
            return false;
        }

        // typecast obj to Recipe so that we can compare data members
        // Compare the ids
        return id.equals((recipeObj.id));
    }*/
}
