package de.hdm.se3project.backend.model;

import de.hdm.se3project.backend.model.enums.Category;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

import javax.persistence.*;


@Document(collection = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String instructions;
    private String [] tags;
    private Category category;
    private String picture;
    private String link;
    //id of the Account that created the recipe
    private String ownerAccount;


    public Recipe () {

    }

    public Recipe(String id, String name, String instructions, String[] tags, String picture, String link, Category category) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.tags = tags;
        this.picture = picture;
        this.link = link;
        this.category = category;
    }

    public String getId() {
        return id;
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

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }
}
