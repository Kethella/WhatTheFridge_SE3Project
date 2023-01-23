package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.models.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {

    Media downloadMedia(String id) throws IOException;
    String uploadMedia(MultipartFile file) throws IOException;

}