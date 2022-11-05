package de.hdm.se3project.backend.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.se3project.backend.model.Recipe;
import org.bson.Document;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsumeExternalApiService {

    public static void main(String[] args) {
        getExternalRecipes();
    }

    private static void getExternalRecipes() {
        //char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m'};
        char[] letters = {'n','o', 'p','q','r','s','t','u','v','w','x','y','z'};


        for (char letter : letters) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("LETTER");
            System.out.println(letter);
            System.out.println();
            System.out.println();
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


        if(!startRecipe.isEmpty()){
            //store the recipes in ArrayList
            ArrayList<String> recipeStrings = new ArrayList<>();
            for (int i = 0; i < startRecipe.size() - 1; i++){
                recipeStrings.add(result.substring(startRecipe.get(i), startRecipe.get(i+1)));
            }
            recipeStrings.add(result.substring(startRecipe.get(startRecipe.size()-1)));

            for (int i = 0; i < recipeStrings.size(); i++){

                getNextRecipe(recipeStrings.get(i));
            }
        }
    }

    public static void getNextRecipe(String recipeString){

        MongoClient client = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase db = client.getDatabase("whatTheFridgeDB");
        MongoCollection col = db.getCollection("externalRecipes");

        //TODO: change later
        Recipe recipe = new Recipe();
        Document sampledoc = new Document("_id", IdGenerationService.generateId(recipe));

        int start;
        int end;
        String rest = recipeString;

        int count = 0;
        try{
            while (count <= 49){

                count++;

                start = rest.indexOf(":") + 1;
                end = rest.indexOf(",\"");

                String temp;

                //count = 1 -> skip (mealId)
                if(count == 2){ //name
                    temp = rest.substring(start+1,end-1);
                    sampledoc.append("name", temp);
                }
                //count = 3 ->
                else if(count == 4){ //category
                    temp = rest.substring(start+1,end-1);
                    sampledoc.append("category", temp);
                }
                //count = 4 ->
                //count = 5 ->
                else if(count == 6){ //instructions
                    temp = rest.substring(start+1,end-1);
                    sampledoc.append("instructions", temp);
                }
                else if(count == 7){ //picture
                    temp = rest.substring(start+1,end-1);
                    sampledoc.append("picture", temp);
                }
                else if(count == 8){ //tags
                    //TODO
                    if(rest.substring(start, end).contains("null")){
                        temp = null;
                    }
                    else{
                        temp = rest.substring(start+1,end-1);
                    }
                    sampledoc.append("tags", temp);
                }
                else if(count == 9){ //link
                    if(rest.substring(start, end).contains("null")){
                        temp = null;
                    }
                    else{
                        temp = rest.substring(start+1,end-1);
                    }
                    sampledoc.append("link", temp);
                }
                else if(count == 10){ //ingredient 1-20
                    temp = (rest.substring(start+1, end-1));
                    rest = rest.substring(end + 1);

                    ArrayList<String> tempArray = new ArrayList<>();
                    tempArray.add(temp);
                    for(int i = 2; i <= 20; i++){

                        count++;

                        start = rest.indexOf(":") + 1;
                        end = rest.indexOf(",\"");

                        if(!rest.substring(start, end).contains("null") && !rest.substring(start+1, end-1).isEmpty() && !rest.substring(start+1, end-1).equals(" ")){
                            temp = temp.concat(",");
                            temp = temp.concat(rest.substring(start+1, end-1));
                            //TODO: change
                            String temp2 = rest.substring(start+1, end-1);
                            tempArray.add(temp2);
                        }

                        if(i != 20){
                            rest = rest.substring(end + 1);
                        }
                    }
                    System.out.println(tempArray);
                    sampledoc.append("ingredientNames", tempArray);
                }
                else if(count == 30){ //measure 1-20
                    temp = rest.substring(start+1, end-1);
                    rest = rest.substring(end + 1);

                    ArrayList<String> tempArray = new ArrayList<>();
                    tempArray.add(temp);
                    //tempArray.clear();
                    for(int i = 2; i <= 20; i++){

                        count++;

                        start = rest.indexOf(":") + 1;
                        end = rest.indexOf(",\"");

                        if(!rest.substring(start, end).contains("null") && !rest.substring(start+1, end-1).isEmpty() && !rest.substring(start+1, end-1).equals(" ")){
                            temp = temp.concat(",");
                            temp = temp.concat(rest.substring(start+1, end-1));
                            //TODO: change
                            String temp2 = rest.substring(start+1, end-1);
                            tempArray.add(temp2);
                        }

                        if(i != 20){
                            rest = rest.substring(end + 1);
                        }
                    }
                    System.out.println(tempArray);
                    sampledoc.append("ingredientMeasures", tempArray);
                }

                rest = rest.substring(end + 1);
            }

            col.insertOne(sampledoc);

        } catch (Exception StringIndexOutOfBoundsException){
            if(!sampledoc.get("name").equals("Breakfast Potatoes")
                    /*&& !sampledoc.get("name").equals("Pilchard puttanesca")*/
                    && !sampledoc.get("name").equals("Salmon Avocado Salad")){
                System.out.println("PROBLEMATIC");
                System.out.println(sampledoc.get("name"));
                System.out.println(rest);
                System.exit(1);
            }

        }

    }
}
