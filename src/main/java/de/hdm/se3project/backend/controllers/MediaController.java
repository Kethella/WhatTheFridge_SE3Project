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
    public String uploadAccountImg(@RequestParam("file")MultipartFile file) throws IOException {
        System.out.println("Upload file");
        return mediaService.uploadMedia(file);
    }
    /*public ResponseEntity<?> uploadAccountImg(@RequestParam("file")MultipartFile file) throws IOException,
            ResourceNotFoundException {
        System.out.println("Upload file");
        System.out.println(new ResponseEntity<>(mediaService.uploadMedia(file), HttpStatus.OK));
        return new ResponseEntity<>(mediaService.uploadMedia(file), HttpStatus.OK);
    }*/

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        Media media = mediaService.downloadMedia(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.getFileType()))
                .body(new ByteArrayResource(media.getFile()));
    }

    @PutMapping("/update/{id}")
    public String updateAccountMedia(@PathVariable String id, @RequestBody MultipartFile file) throws IOException {
       return mediaService.updateMedia(id, file);
   }

    @DeleteMapping("/delete/{id}")
    public String deleteAccountImg(@PathVariable("id") String id) throws IOException {
        mediaService.deleteMedia(id);
        return "Media deleted";
    }

}