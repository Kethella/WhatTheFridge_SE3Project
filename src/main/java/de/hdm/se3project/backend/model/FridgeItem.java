package de.hdm.se3project.backend.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "fridgeItems")
public class FridgeItem {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private int amount;

    private String expirationDate;

    @NonNull
    private String ownerAccount;

    public FridgeItem() {
        ownerAccount = " ";
        name = " ";
        amount = 0;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FridgeItem that = (FridgeItem) obj;
        return amount == that.amount && name.equals(that.name) && Objects.equals(expirationDate, that.expirationDate) && ownerAccount.equals(that.ownerAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, expirationDate, ownerAccount);
    }
}

