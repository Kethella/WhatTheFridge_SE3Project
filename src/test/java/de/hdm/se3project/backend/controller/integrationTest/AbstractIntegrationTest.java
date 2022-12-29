package de.hdm.se3project.backend.controller.integrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

//PS: using @DataMongoTest because with @SpringBootTest the test objects goes to my real DB
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  //Notation to make it run as a test
//@ActiveProfiles("test")

@Testcontainers //need to enable to run the tc Junit 5 into test container mode --> it runs all containers annotated with @container
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public abstract class AbstractIntegrationTest {

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

}
