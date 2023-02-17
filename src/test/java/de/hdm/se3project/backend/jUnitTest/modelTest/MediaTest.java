package de.hdm.se3project.backend.jUnitTest.modelTest;

import de.hdm.se3project.backend.models.Media;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Description("Testing model class: Media")
public class MediaTest {

    Media media;

    byte[] byteMedia = {};

    Media MEDIA_1 = new Media("fileName", "fileType", "fileSize", byteMedia);

    @BeforeEach
    void setUp() { media = new Media(); }

    @Test
    void mediaTest() {
        media = MEDIA_1;
        assertEquals("fileName", media.getFileName());
        assertEquals("fileType", media.getFileType());
        assertEquals("fileSize", media.getFileSize());
        assertArrayEquals(byteMedia, media.getFile());
    }

    @Test
    void mediaNotNullTest(){
        media = MEDIA_1;
        assertNotNull(media.getFileName());
        assertNotNull(media.getFileType());
        assertNotNull(media.getFileSize());
        assertNotNull(media.getFile());
    }
}
