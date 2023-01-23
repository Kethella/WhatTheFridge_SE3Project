package de.hdm.se3project.backend.controller.jUnitTest.controllerTest;

import de.hdm.se3project.backend.controller.MediaController;
import de.hdm.se3project.backend.services.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(MediaController.class)
public class MediaControllerTest {

    /*private MockMvc mockMvc;

    //@Mock
    //private MediaService mediaService;

    //@InjectMocks
    @Autowired
    private MediaController mediaController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(mediaController).build();
    }

    //Creating a method that simulates a file upload
    @Test
    public void testUpload() throws Exception {
        MockMultipartFile fileTest = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        //using MockMvc to perform a multipart/form-data POST request to the endpoint of the MediaController class that handles file uploads.
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(fileTest))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded and processed."));
    }

    @Test
    void downloadTest() {
        //Test here
    }
*/
}
