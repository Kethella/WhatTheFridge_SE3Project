package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Media;
import de.hdm.se3project.backend.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    //@RequestMapping(value = "/upload", method = RequestMethod.POST)
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

}