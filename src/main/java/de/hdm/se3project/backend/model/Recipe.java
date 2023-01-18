package de.hdm.se3project.backend.model;

import de.hdm.se3project.backend.model.enums.Category;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.Objects;


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

    public String getImage() {
        return picture;
    }

    public void setImage(String picture) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) && category == recipe.category && Objects.equals(instructions, recipe.instructions) && Objects.equals(picture, recipe.picture) && Arrays.equals(tags, recipe.tags) && Objects.equals(link, recipe.link) && Arrays.equals(ingredientNames, recipe.ingredientNames) && Arrays.equals(ingredientMeasures, recipe.ingredientMeasures) && Objects.equals(ownerAccount, recipe.ownerAccount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, category, instructions, picture, link, ownerAccount);
        result = 31 * result + Arrays.hashCode(tags);
        result = 31 * result + Arrays.hashCode(ingredientNames);
        result = 31 * result + Arrays.hashCode(ingredientMeasures);
        return result;
    }
}