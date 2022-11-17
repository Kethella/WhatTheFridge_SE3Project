package de.hdm.se3project.backend.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import de.hdm.se3project.backend.controller.AccountController;
import de.hdm.se3project.backend.model.File;
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
public class FileService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    private AccountController accountController;

    public String addFile(String owner, MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("owner", owner);

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }


    public File downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        File file = new File();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            file.setFilename( gridFSFile.getFilename() );

            file.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            file.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            file.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );

            file.setOwnerAccount(gridFSFile.getMetadata().get("owner").toString());
        }

        return file;
    }

}
