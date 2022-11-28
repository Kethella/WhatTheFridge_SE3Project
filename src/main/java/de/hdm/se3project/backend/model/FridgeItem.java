package de.hdm.se3project.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fridgeItems")
public class FridgeItem {

    @Id
    private String id;
    private String name;
    private int amount;
    private String expirationDate;
    private String ownerAccount;

    public FridgeItem() {
    }

    public FridgeItem(String id, String name, int amount, String expirationDate, String ownerAccount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.expirationDate = expirationDate;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }
}

