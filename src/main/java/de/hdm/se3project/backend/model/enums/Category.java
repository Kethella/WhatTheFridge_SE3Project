package de.hdm.se3project.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum Category{


    @JsonProperty("MAINCOURSE")
    MAINCOURSE("Main Course"),
    @JsonProperty("DESSERT")
    DESSERT("Dessert"),
    @JsonProperty("SIDE")
    SIDE("Side"),
    @JsonProperty("STARTER")
    STARTER("Starter"),
    @JsonProperty("DRINKS")
    DRINKS("Drinks"),
    @JsonProperty("BREAKFAST")
    BREAKFAST("Breakfast");

    private String text;

    Category(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}