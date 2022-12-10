package de.hdm.se3project.backend.model;


import org.springframework.data.mongodb.core.mapping.DocumentReference;

//not defining a document here since the file will directly get stored in the GridFS collections (fs.files, fs.chunks)
public class Media {

    private String fileName;
    private String fileType;
    private String fileSize;
    private byte[] file;

    public Media() {
    }

    public Media(String filename, String fileType, String fileSize, byte[] file) {
        this.fileName = filename;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.file = file;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
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
}
