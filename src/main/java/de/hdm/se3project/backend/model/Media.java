package de.hdm.se3project.backend.model;


import org.springframework.data.mongodb.core.mapping.DocumentReference;

//not defining a document here since the file will directly get stored in the GridFS collections (fs.files, fs.chunks)
public class Media {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;

    public Media() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
