package de.hdm.se3project.backend.controller.integrationTest.servicesTest;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.model.enums.Category;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.repository.RecipeRepository;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import de.hdm.se3project.backend.services.impl.RecipeServiceImpl;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

//@Testcontainers//need to enable to run the tc Junit 5 into test container mode --> it runs all containers annotated with @container
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)

public class RecipeServiceImpTest {

    @Autowired
    private RecipeRepository recipeRepository;
    private RecipeServiceImpl recipeServiceImpl;

    /*@Container  //creating a mongoDB container obj and keeping it until the end of all tests, then it will be deleted
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest")); //class MongoDBContainer coming through library passing desired Docker image

    @DynamicPropertySource //Connecting to our local dockerized MongoDB instance
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @BeforeAll //starting the container
    static void initAll(){
        container.start();
    }

    @BeforeEach
    void setUp(){
        this.recipeServiceImpl = new RecipeServiceImpl(recipeRepository);
    }
*/
    @AfterEach
    void cleanUp(){
        this.recipeRepository.deleteAll();
    }

   /* @Test
    @Description("Check if Container has started and the public port is available")
    void containerStartsAndPublicPortIsAvailable() {
        assertThatPortIsAvailable(container);
    }*/

    @Test
    @Description("Checks method getAllRecipes in RecipeServiceImpl class")
    void shouldGetAllRecipes() {
        Recipe recipe1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
                Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipe2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);

        List<Recipe> result = recipeServiceImpl.getAllRecipes();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @Description("Checks method updateRecipe in RecipeServiceImpl class")
    void shouldUpdateRecipe() throws ResourceNotFoundException {
        Recipe recipe = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipeOrig = this.recipeRepository.save(recipe);

        recipeOrig.setName("Chocolate Cake with fruit");
        recipeOrig.setInstructions("instructions - how to make a chocolate cake with fruits");

        Recipe recipeUpdate = this.recipeServiceImpl.updateRecipe(recipe.getId(), recipeOrig);
        Assertions.assertEquals("Chocolate Cake with fruit", recipeUpdate.getName());
        Assertions.assertEquals("instructions - how to make a chocolate cake with fruits", recipeUpdate.getInstructions());
    }

    @Test
    @Description("Checks method deleteRecipe in RecipeServiceImpl class")
    void shouldDeleteRecipe() throws ResourceNotFoundException {
        Recipe recipe1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
                Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipe2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);

        this.recipeServiceImpl.deleteRecipe(recipe1.getId());

        List<Recipe> recipes = recipeServiceImpl.getAllRecipes();
        Assertions.assertEquals(1, recipes.size());
    }

    @Test
    @Description("Checks method getRecipesByOwnerAccount in RecipeServiceImpl class")
    void shouldGetRecipesByOwnerAccount() {
        Recipe recipe1 = new Recipe("1", "Pasta", "instructions - how to make pasta",
                Category.MAINCOURSE, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "1");

        Recipe recipe2 = new Recipe("2", "Chocolate Cake", "instructions - how to make a chocolate cake",
                Category.DESSERT, new String[]{"tag 01"}, "image", "link", new String[]{"ingredients 01",
                "ingredients 02"}, new String[]{"measures 01", "measures 02"}, "2");

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);

        List<Recipe> recipes = recipeServiceImpl.getAllRecipes();

        List<Recipe> result = recipeServiceImpl.getRecipesByOwnerAccount("2", recipes);
        Assertions.assertEquals(1, result.size());
    }

    /*protected void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in host and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getHost(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }*/
}
