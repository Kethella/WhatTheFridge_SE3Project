package de.hdm.se3project.backend.services.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import de.hdm.se3project.backend.models.Media;
import de.hdm.se3project.backend.services.MediaService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    @Override
    public Media downloadMedia(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        Media media = new Media();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {

            media.setFileName( gridFSFile.getFilename() );
            media.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );
            media.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return media;
    }

    @Override
    public String uploadMedia(MultipartFile file) throws IOException {
        DBObject metadata = new BasicDBObject();
        Object fileID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
        String imageId = fileID.toString();
        String link = "http://localhost:8085/media/download/";
        return link.concat(imageId);
    }

    public void deleteMedia(MultipartFile file) throws IOException {
        //TODO
    }

}