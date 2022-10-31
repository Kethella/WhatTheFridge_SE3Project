package de.hdm.se3project.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fridgeItems")
public class FridgeItem {

    private String idItem;
    private String nameItem;
    private int amountItem;
    private String expirationDate;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public int getAmountItem() {
        return amountItem;
    }

    public void setAmountItem(int amountItem) {
        this.amountItem = amountItem;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

}
