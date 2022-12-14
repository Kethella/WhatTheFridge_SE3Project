package de.hdm.se3project.backend.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.model.Recipe;
import org.bson.Document;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.include;

/* ID generation for the DB objects (Account, FridgeItem, Recipe)
 * author: ag186
 */
public class IdGenerationService {

    //not optimal but ObjectId made me suffer:/
    public static String generateId(Object obj){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssnnnnnnnnnn");
        String candidateId = LocalTime.now().format(dtf);

        if (Account.class.isAssignableFrom(obj.getClass()) && existsInAccountsDB(candidateId)){
            generateId(obj);
        }
        else if (Recipe.class.isAssignableFrom(obj.getClass()) && existsInRecipesDB(candidateId)){
            generateId(obj);
        }
        else if (FridgeItem.class.isAssignableFrom(obj.getClass()) && existsInFridgeItemsDB(candidateId)){
            generateId(obj);
        }

        return candidateId;
    }

    public static boolean existsInAccountsDB(String candidateId){
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority")) {
            MongoCollection<Document> coll = mongoClient.getDatabase("whatTheFridgeDB").getCollection("accounts");
            if (checkAllUntilIdMatch(candidateId, coll)) return true;
        }

        return false;
    }

    public static boolean existsInRecipesDB(String candidateId){
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority")) {
            MongoCollection<Document> coll = mongoClient.getDatabase("whatTheFridgeDB").getCollection("recipes");
            if (checkAllUntilIdMatch(candidateId, coll)) return true;

        }

        return false;
    }

    public static boolean existsInFridgeItemsDB(String candidateId){
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority")) {
            MongoCollection<Document> coll = mongoClient.getDatabase("whatTheFridgeDB").getCollection("fridgeItems");
            if (checkAllUntilIdMatch(candidateId, coll)) return true;

        }

        return false;
    }

    private static boolean checkAllUntilIdMatch(String candidateId, MongoCollection<Document> coll) {
        List<Document> docs = coll.find().projection(include("_id")).into(new ArrayList<>());

        for (Document doc : docs){
            if (candidateId.equals(String.valueOf(doc.get("_id")))){
                //TODO: log
                return true;
            }
            else{
                //TODO: log
            }
        }
        return false;
    }

}