package de.hdm.se3project.backend.controller.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.hdm.se3project.backend.controller.MediaController;
import de.hdm.se3project.backend.model.Media;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MediaControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private MediaService mediaService;

    @InjectMocks
    private MediaController mediaController;

    //@Autowired
    //private WebApplicationContext webApplicationContext;

    public MediaControllerTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mediaController).build();
        //this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void uploadMediaTest() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());


        Mockito.when(mediaService.uploadMedia(file)).thenReturn(String.valueOf(file));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/media/upload")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        /**
        String contentStr = objectWriter.writeValueAsString(file);

        MockHttpServletRequestBuilder mockRequest
                = MockMvcRequestBuilders.post("/media/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contentStr);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

         */
    }

    @Test
    void downloadTest() {
        //Test here
    }

}
