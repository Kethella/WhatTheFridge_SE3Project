package de.hdm.se3project.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum Category{

    @JsonProperty("C1")
    C1("Beef"),
    @JsonProperty("C2")
    C2("Chicken"),
    @JsonProperty("C3")
    C3("Dessert"),
    @JsonProperty("C4")
    C4("Lamb"),
    @JsonProperty("C5")
    C5("Miscellaneous"),
    @JsonProperty("C6")
    C6("Pasta"),
    @JsonProperty("C7")
    C7("Pork"),
    @JsonProperty("C8")
    C8("Seafood");


    private String text;

    Category(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}