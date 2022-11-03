package de.hdm.se3project.backend.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.se3project.backend.model.Recipe;
import org.bson.Document;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsumeExternalApiService {

    public static void main(String[] args) {
        getExternalRecipes();
    }

    private static void getExternalRecipes() {
        final String uri = "https://www.themealdb.com/api/json/v1/1/search.php?f=a";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        restTemplate.getForEntity(uri, String.class);

        System.out.println(result);


        Pattern recipe = Pattern.compile("\"idMeal\"");
        Matcher matcher = recipe.matcher(result);

        //find where recipe starts
        ArrayList<Integer> startRecipe = new ArrayList<>();
        while (matcher.find()) {
            startRecipe.add(matcher.start());
        }

        //store the recipes in ArrayList
        ArrayList<String> recipeStrings = new ArrayList<>();
        for (int i = 0; i < startRecipe.size() - 1; i++){
            recipeStrings.add(result.substring(startRecipe.get(i), startRecipe.get(i+1)));
        }
        recipeStrings.add(result.substring(startRecipe.get(startRecipe.size()-1)));

        for (int i = 0; i < recipeStrings.size(); i++){

            getNextValue(recipeStrings.get(i));
        }

    }

    public static void getNextValue(String recipeString){
        int start;
        int end;
        String rest = recipeString;

        Recipe externalRecipe = new Recipe();

        int count = 0;
        while (count <= 49){

            count++;

            start = rest.indexOf(":") + 1;
            end = rest.indexOf(",\"");

            String temp;

            //count = 1 -> skip (mealId)
            if(count == 2){ //name
                temp = rest.substring(start+1,end-1);
                externalRecipe.setName(temp);
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 4){ //category
                temp = rest.substring(start+1,end-1);
                externalRecipe.setName(temp);
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 6){ //instructions
                temp = rest.substring(start+1,end-1);
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 7){ //image
                temp = rest.substring(start+1,end-1);
                externalRecipe.setName(temp);
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 8){ //tags
                if(rest.substring(start, end).contains("null")){
                    temp = null;
                }
                else{
                    temp = rest.substring(start+1,end-1);
                }
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 9){ //link
                if(rest.substring(start, end).contains("null")){
                    temp = null;
                }
                else{
                    temp = rest.substring(start+1,end-1);
                }
                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 10){ //ingredient 1-20
                temp = (rest.substring(start+1, end-1));
                rest = rest.substring(end + 1);

                for(int i = 2; i <= 20; i++){
                    count++;

                    start = rest.indexOf(":") + 1;
                    end = rest.indexOf(",\"");

                    if(!rest.substring(start, end).contains("null") && !rest.substring(start+1, end-1).isEmpty() && !rest.substring(start+1, end-1).equals(" ")){
                        temp = temp.concat(",");
                        temp = temp.concat(rest.substring(start+1, end-1));
                    }

                    if(count != 20){
                        rest = rest.substring(end + 1);
                    }
                }

                System.out.println(temp);
                System.out.println(rest);
            }
            else if(count == 30){ //measure 1-20
                temp = rest.substring(start+1, end-1);
                rest = rest.substring(end + 1);

                for(int i = 2; i <= 20; i++){
                    count++;

                    start = rest.indexOf(":") + 1;
                    end = rest.indexOf(",\"");

                    if(!rest.substring(start, end).contains("null") && !rest.substring(start+1, end-1).isEmpty() && !rest.substring(start+1, end-1).equals(" ")){
                        temp = temp.concat(",");
                        temp = temp.concat(rest.substring(start+1, end-1));
                    }

                    if(count != 49){
                        rest = rest.substring(end + 1);
                    }
                }

                System.out.println(temp);
                System.out.println(rest);
            }

            rest = rest.substring(end + 1);
        }

        System.out.println();
        System.out.println("NEW");
    }

    public static void example(){
        MongoClient client = MongoClients.create("mongodb+srv://root:passWord@learnstuff.cec5wbi.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase db = client.getDatabase("whatTheFridgeDB");
        MongoCollection col = db.getCollection("externalRecipes");

        Document sampledoc = new Document("_id", "2").append("name", "hii");
        sampledoc.append("surname", "no");

        col.insertOne(sampledoc);
    }
}
