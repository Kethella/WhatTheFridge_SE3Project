package de.hdm.se3project.backend.jUnitTest.controllerTest;

import de.hdm.se3project.backend.Application;
import de.hdm.se3project.backend.controllers.MediaController;
import de.hdm.se3project.backend.models.Media;
import de.hdm.se3project.backend.services.MediaService;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration //Indicates that a test class is a web application context configuration class
@ContextConfiguration(classes = Application.class) //Load the context for the test
public class MediaControllerTest {

    //Observations:
    //1. The MediaServiceImpl class - that implements MediaService class - is already annotated with @Service
    //2.@SpringBootApplication annotation already applies @ComponentScan on class Application - in which is responsible
    // for telling Spring where to look for beans (components, services, etc.) in the application.
    //3.MediaService is in the MediaController class autowired.

    private MockMvc mockMvc;

    @Mock
    private MediaService mediaService;

    @InjectMocks
    private MediaController mediaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(mediaController).build();
    }

    //The test method is sending a POST request to the "/media/upload" endpoint, and it's
    // using the MockMultipartFile class to simulate a multipart file upload.
    // The test method is also asserting that the HTTP status of the response is 200 (OK) and
    // that the message in the response body is "Successfully uploaded - test.txt"
    @Test
    @Description("Testing POST request to the \"/media/upload\" endpoint")
    public void testUpload() throws Exception {
        // Create a mock file
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "This is a test file.".getBytes());

        when(mediaService.uploadMedia(file)).thenReturn(String.valueOf(file));

        // Perform the request
        mockMvc.perform(multipart("/media/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(file)))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(mediaService, times(1)).uploadMedia(file);
    }

    @Test
    @Description("The test method is sending a GET request to the \"/media/download/{id}\" endpoint, " +
            "and it's asserting that the HTTP status of the response is 200 (OK)")
    public void testDownload() throws Exception {
        Media media = new Media("test.txt", "text/plain", "size", "byte".getBytes());
        when(mediaService.downloadMedia(media.getFileName())).thenReturn(media); //It simulates a media file that is going to be returned by the service method

        mockMvc.perform(get("/media/download/{id}", media.getFileName()))  //Passing file name as id
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN)) //Text/plain = .txt file type
                .andExpect(MockMvcResultMatchers.content().bytes(media.getFile()))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(mediaService, times(1)).downloadMedia(media.getFileName());
    }

    //TODO
    /*@Test
    @Description("")
    public void testUpdateAccountImg() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "This is a test file.".getBytes());

        //when(mediaService.updateMedia(file.getName(), file)).thenReturn(String.valueOf(file)); //It simulates a media file that is going to be returned by the service method

        mockMvc.perform(put("/media/update/{id}", file.getName())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(file.getBytes()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(mediaService, times(1)).updateMedia(file.getName(), file);
    }*/

    @Test
    @Description("Testing deleteAccountImg method")
    public void testDeleteAccountImg() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "This is a test file.".getBytes());

        mockMvc.perform(delete("/media/delete/{id}", file.getName())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(file.getBytes()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        verify(mediaService, times(1)).deleteMedia(file.getName());
    }

}

