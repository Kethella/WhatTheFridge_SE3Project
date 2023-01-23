package de.hdm.se3project.backend.controllers;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Media;
import de.hdm.se3project.backend.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController  //Notation used to make it eligible for this class to handle HTTP requests.
@RequestMapping("/media") //Defining the endpoint URLs that the MediaController will handle.
@CrossOrigin(origins = "http://localhost:4200")
public class MediaController {

    @Autowired //This allows the MediaController to make use of the MediaService's methods.
    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> uploadAccountImg(@RequestParam("file")MultipartFile file) throws IOException{
        return new ResponseEntity<>(mediaService.uploadMedia(file), HttpStatus.OK);
    }

    //"/media/upload" with POST request method, which maps to the uploadAccountImg(file) method. It takes a
    // MultipartFile as parameter and store the file in the server and returns a ResponseEntity indicating
    // whether the file was successfully uploaded or not.
    /*@RequestMapping(value = "/media/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadAccountImg(@RequestParam("file") MultipartFile file) {
        try {
            mediaService.uploadMedia(file);
            return new ResponseEntity<>("Successfully uploaded - " + file.getOriginalFilename(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload - " + file.getOriginalFilename() + "!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/


    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        Media media = mediaService.downloadMedia(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.getFileType()))
                .body(new ByteArrayResource(media.getFile()));
    }

}