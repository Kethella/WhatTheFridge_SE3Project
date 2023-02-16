package de.hdm.se3project.backend.models;

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
}