package de.hdm.se3project.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fridgeItems")
public class FridgeItem {

    private String idItem;
    private String nameItem;
    private int amountItem;
    private String ExpirationDate;

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
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }





    //COMMENTS FOR MYSELF

    //GET --> usar localhost enviado no whatsapp
    //GetAllAccounts postman (Para colocar coisas por nós mesmos - like the ITEM VALUE IN MONGODB)

    //ITEM VALUE
    //id          (id for the item itself and // (work later) account id to refer to the item in the user fridge).
    //name
    //amount
    //valid date  //put as string now and change it later as Date type, because in the JASON file there is no DATE type

    //P.S.
    //my fridge is my collection on the Data Base (do not need to create an object yet)
    //try to make user put a valid input (not mandatory in the moment, do it later)

    // WHAT SHOULD I DO:

    // 1° controler package (class: ItemController)  -> ( post, create,  delete, get mappings for my classes)

    // 2° class: geters and setters to get the ingredients values

    // 3° repository

}
