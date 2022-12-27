package de.hdm.se3project.backend.model;


import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

//not defining a document here since the file will directly get stored in the GridFS collections (fs.files, fs.chunks)
public class Media {

    private String fileName;
    private String fileType;
    private String fileSize;
    private byte[] file;

    public Media() {
    }

    public Media(String fileName, String fileType, String fileSize, byte[] file) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    /**
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Media media = (Media) obj;
        return Objects.equals(fileName, media.fileName) && Objects.equals(fileType, media.fileType) && Objects.equals(fileSize, media.fileSize) && file == media.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, fileType, fileSize, Arrays.hashCode(file));
    }
    */
}