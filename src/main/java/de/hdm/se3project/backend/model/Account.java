package de.hdm.se3project.backend.model;

import de.hdm.se3project.backend.model.enums.SecurityQuestion;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


/* Model class for "accounts" MongoDB collection
* author: ag186
*/
@Document(collection = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String email;
    private String password;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;

    /*https://spring.io/blog/2021/11/29/spring-data-mongodb-relation-modelling

    This lets you store personalRecipes without having to think about updating the Account document.
    To do so, we need to do two things.
    First, we need to tell the mapping layer to omit storing links from Account to Recipe and
    second, update the lookup query when retrieving linked personalRecipes.

    The initial part is rather easy, applying an additional @ReadOnlyPorperty annotation to the personalRecipes property.
    The other part requires us to update the lookup attribute of the @DocumentReference annotation with a custom query:
    */
    @ReadOnlyProperty
    @DocumentReference(lookup="{'ownerAccount':?#{#self._id} }")
    private List<Recipe> personalRecipes;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'ownerAccount':?#{#self._id} }")
    private List<FridgeItem> fridgeItems;

    public Account() {

    }

    public Account(String id, String name, String email, String password, SecurityQuestion securityQuestion, String securityAnswer, List<Recipe> personalRecipes, List<FridgeItem> fridgeItems) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.personalRecipes = personalRecipes;
        this.fridgeItems = fridgeItems;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public List<Recipe> getPersonalRecipes() {
        return personalRecipes;
    }

    public void setPersonalRecipes(List<Recipe> personalRecipes) {
        this.personalRecipes = personalRecipes;
    }

    public List<FridgeItem> getFridgeItems() {
        return fridgeItems;
    }

    public void setFridgeItems(List<FridgeItem> fridgeItems) {
        this.fridgeItems = fridgeItems;
    }
}
