package de.hdm.se3project.backend.controller.integrationTest.servicesTest;


import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers//need to enable to run the tc Junit 5 into test container mode --> it runs all containers annotated with @container
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class FridgeItemServiceTest {
/**
    @Autowired
    private FridgeItemRepository fridgeItemRepository;
    private FridgeItemServiceImpl fridgeItemServiceImpl;

     @Container  //creating a mongoDB container obj and keeping it until the end of all tests, then it will be deleted
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
        this.fridgeItemServiceImpl = new FridgeItemServiceImpl(fridgeItemRepository);
    }

    @AfterEach
    void cleanUp(){
        this.fridgeItemRepository.deleteAll();
    }

    @Test
    @Description("Checks method getFridgeItems in FridgeItemServiceImpl class")
    void shouldGetFridgeItems() {
        FridgeItem fridgeItem1 = new FridgeItem("1", "butter", 1, "22.01.2023", "1");
        FridgeItem fridgeItem2 = new FridgeItem("2", "banana", 6, "26.01.2023", "2");

        this.fridgeItemRepository.save(fridgeItem1);
        this.fridgeItemRepository.save(fridgeItem2);

        List<FridgeItem> result = fridgeItemServiceImpl.getFridgeItems();
        Assertions.assertEquals(2, result.size());
    }


    /**
    @Test
    void shouldCreateFridgeItem(){
        FridgeItem fridgeItem = new FridgeItem();

        fridgeItem.setName("chocolate milk");
        fridgeItem.setAmount(1);
        fridgeItem.setExpirationDate("19.01.2023");
        fridgeItem.setOwnerAccount("3");

        FridgeItem fridgeItem1 = this.fridgeItemRepository.save(fridgeItem); //not saving in the actual Database, because container is being used
        Assertions.assertEquals("apple", fridgeItem1.getName());
    }

    */

    /**
     * @Override
     *     public FridgeItem createFridgeItem(FridgeItem item) {
     *         item.setId(IdGenerationService.generateId(item));
     *         return fridgeItemRepository.save(item);
     *     }
     *
     *
 *     protected void assertThatPortIsAvailable(MongoDBContainer container){
 *         try { //container will start in host and run in the port number, if it is running fine, if not, create exception
 *             new Socket(container.getHost(), container.getFirstMappedPort());
 *         } catch (IOException e) {
 *             throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
 *         }
 *     }
     */



}
