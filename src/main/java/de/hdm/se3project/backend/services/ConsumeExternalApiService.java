package de.hdm.se3project.backend.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import org.bson.Document;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * The class stores the result from a series of GET requests as a Very Big String.
 * The Very Big String contains all the recipes beginning with a certain letter.
 * Then the class separates the Very Big String into a List of individual recipes (Big Strings).
 * The Big Strings contain all the info for a certain recipe.
 * Then the class separates the Big String into values of the different properties of the recipe
 * and saves the recipe and the values of its properties as objects in the database.
 *
 * THE CLASS IS NOT TO BE RUN WITH THE APPLICATION EVERY TIME.
 *
 * author: ag186
 */
public class ConsumeExternalApiService {

    private static String rest;
    private static int count;

    static List<String> allTags = new ArrayList<>();

    public static void main(String[] args) {
        getExternalRecipes();
    }

    private static void getExternalRecipes() {
        //split into 3 to deal with timeouts
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        //char[] letters = {'i', 'j', 'k', 'l', 'm', 'n','o', 'p',};
        //char[] letters = {'q','r','s','t','u','v','w','x','y','z'};


        for (char letter : letters) {
            getRecipesWithNextLetter(letter);
        }
    }

    public static void getRecipesWithNextLetter(char letter){
        String unfinishedURI = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
        final String uri = unfinishedURI.concat(String.valueOf(letter));

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        restTemplate.getForEntity(uri, String.class);

        Pattern recipe = Pattern.compile("\"idMeal\"");
        Matcher matcher = recipe.matcher(result);

        //find where recipe starts
        ArrayList<Integer> startRecipe = new ArrayList<>();
        if(matcher.find()){
            while (matcher.find()) {
                startRecipe.add(matcher.start());
            }
        }

        //store the recipes in ArrayList
        if(!startRecipe.isEmpty()){
            ArrayList<String> recipeStrings = new ArrayList<>();

            for (int i = 0; i < startRecipe.size() - 1; i++){
                recipeStrings.add(result.substring(startRecipe.get(i), startRecipe.get(i+1)));
            }
            recipeStrings.add(result.substring(startRecipe.get(startRecipe.size()-1)));

            for (String recipeString : recipeStrings) {
                getNextRecipe(recipeString);
            }
        }
    }

    public static void getNextRecipe(String recipeString){

        MongoClient client = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase db = client.getDatabase("whatTheFridgeDB");
        //MongoCollection<Document> col = db.getCollection("externalRecipes");
        MongoCollection<Document> col = db.getCollection("recipes");

        Recipe recipeObj = new Recipe();
        Document recipe = new Document("_id", IdGenerationService.generateId(recipeObj));


        allTags.clear();
        rest = recipeString;
        count = 0;
        try{
            while (count <= 49){

                count++;

                //count = 1 -> skip (mealId)
                if(count == 2){ //name
                    recipe.append("name", getValue());
                }
                //count = 3 -> skip (strDrinkAlternate)
                else if(count == 4){ //category
                    Category category = getCategory(getValue());
                    recipe.append("category", category);
                }
                //count = 5 -> skip (strArea)
                else if(count == 6){ //instructions
                    recipe.append("instructions", getValue());
                }
                else if(count == 7){ //picture
                    recipe.append("picture", getValue());
                }
                else if(count == 8){ //tags
                    if(rest.substring(getStart(), getEnd()).contains("null")){
                        recipe.append("tags", null);
                    }
                    else{
                        getTags();
                        recipe.append("tags", allTags);
                    }
                }
                else if(count == 9){ //link
                    if(rest.substring(getStart(), getEnd()).contains("null")){
                        recipe.append("link", null);
                    }
                    else{
                        recipe.append("link", getValue());
                    }
                }
                else if(count == 10){ //ingredient 1-20
                    recipe.append("ingredientNames", getIngredientsNamesOrMeasures());
                }
                else if(count == 30){ //measure 1-20
                    recipe.append("ingredientMeasures", getIngredientsNamesOrMeasures());
                }

                removeUsedSubstring();
            }
            recipe.append("ownerAccount", null);
            col.insertOne(recipe);

        } catch (Exception StringIndexOutOfBoundsException){
            if(!recipe.get("name").equals("Breakfast Potatoes")
                    && !recipe.get("name").equals("Salmon Avocado Salad")){
                System.out.println("PROBLEMATIC");
                System.out.println(recipe.get("name"));
                System.out.println(rest);
                System.exit(1);
            }
        }
    }

    public static String getValue(){

        int start = getStart();
        int end = getEnd();

        return rest.substring(start+1,end-1);
    }

    public static int getStart(){

        return rest.indexOf(":") + 1;
    }

    public static int getEnd(){

        return rest.indexOf(",\"");
    }

    public static void removeUsedSubstring(){
        rest = rest.substring(getEnd() + 1);
    }

    public static Category getCategory(String categoryString) throws ResourceNotFoundException {

        switch (categoryString) {
            case "Beef", "Chicken", "Goat", "Lamb", "Miscellaneous", "Pasta", "Pork", "Seafood", "Vegetarian", "Vegan" -> {
                allTags.add(categoryString.toLowerCase());
                return Category.MAINCOURSE;
            }
            case "Breakfast" -> {
                return Category.BREAKFAST;
            }
            case "Dessert" -> {
                return Category.DESSERT;
            }
            case "Side" -> {
                return Category.SIDE;
            }
            case "Starter" -> {
                return Category.STARTER;
            }
            default -> throw new ResourceNotFoundException("Enum not found");
        }
    }

    public static void getTags(){
        ArrayList<String> candidateTags = new ArrayList<>();

        String restTags = getValue();
        while (restTags.contains(",")){
            String tag = restTags.substring(0, restTags.indexOf(",")).toLowerCase();
            if(!candidateTags.contains(tag)){
                candidateTags.add(tag);
            }
            restTags = restTags.substring(restTags.indexOf(",") + 1);
        }
        candidateTags.add(restTags.toLowerCase());


        List<String> approvedTags = new ArrayList<>();
        for(String candidateTag: candidateTags){
            if(!allTags.contains(candidateTag)){
                approvedTags.add(candidateTag);
            }
        }
        allTags.addAll(approvedTags);
    }

    public static List<String> getIngredientsNamesOrMeasures(){
        List<String> tempArray = new ArrayList<>();

        for(int i = 1; i <= 20; i++){

            if(!rest.substring(getStart(), getEnd()).contains("null") && !getValue().isEmpty() && !getValue().equals(" ")){
                String temp = getValue();
                tempArray.add(temp);
            }

            if(i != 20){
                count++;
                removeUsedSubstring();
            }
        }

        return tempArray;
    }

}
