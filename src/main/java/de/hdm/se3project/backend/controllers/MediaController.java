package de.hdm.se3project.backend.controllers;

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

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        Media media = mediaService.downloadMedia(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.getFileType()))
                .body(new ByteArrayResource(media.getFile()));
    }

    //TODO
    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<Media> updateAccountMedia(@PathVariable("id") String id, @RequestBody Media media) throws IOException {
        // Find the existing media object by id
        Media existingMedia = mediaService.updateMedia(id, (MultipartFile) media);
        if (existingMedia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Update the existing media object with the new information
        existingMedia.setFileName(media.getFileName());
        existingMedia.setFile(media.getFile());
        // Save the updated media object
        mediaService.updateMedia(existingMedia.getFileName(), (MultipartFile) existingMedia);
        return new ResponseEntity<>(existingMedia, HttpStatus.OK);
    }*/
   /* public ResponseEntity<?> updateAccountImg(@PathVariable String id, @RequestBody MultipartFile file) throws IOException {
        return new ResponseEntity<>(mediaService.updateMedia(id, file), HttpStatus.OK);//Return a ResponseEntity object with the updated media as the body and an appropriate HTTP status code.
    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccountImg(@PathVariable("id") String id) throws IOException {
        mediaService.deleteMedia(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}