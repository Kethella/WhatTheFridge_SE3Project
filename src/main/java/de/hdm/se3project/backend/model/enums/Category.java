package de.hdm.se3project.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum Category{


    @JsonProperty("C1")
    C1("Chicken");

    private String text;

    Category(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}