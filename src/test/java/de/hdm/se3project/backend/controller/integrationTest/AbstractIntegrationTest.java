package de.hdm.se3project.backend.controller.integrationTest;

import org.junit.jupiter.api.BeforeAll;
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

    //Used on containerStartsAndPublicPortIsAvailable test classes in each repository test
    protected void assertThatPortIsAvailable(MongoDBContainer container){
        try { //container will start in host and run in the port number, if it is running fine, if not, create exception
            new Socket(container.getHost(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available");
        }
    }
}
