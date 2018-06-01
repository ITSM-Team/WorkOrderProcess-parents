package com.citsh.file.service.impl;

import com.citsh.file.service.GridFSService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GridFSServiceImpl
  implements GridFSService
{
  private Logger logger = LoggerFactory.getLogger(GridFSServiceImpl.class);

  @Autowired
  private GridFsTemplate gridFsTemplate;

  public String saveFile(String tenanId, String taskId, Long userId, File file, String filename)
  {
    String fileId = null;
    try {
      InputStream content = new FileInputStream(file);

      DBObject metadata = new BasicDBObject();
      metadata.put("tenanId", tenanId);
      metadata.put("createTime", new Date());
      metadata.put("userId", userId);
      metadata.put("taskId", taskId);
      GridFSFile gridFSFile = this.gridFsTemplate.store(content, filename, filename
        .substring(filename
        .lastIndexOf(".")), 
        metadata);
      fileId = gridFSFile.getId().toString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return fileId;
  }

  public GridFSFile getFile(String fileid)
  {
    return this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileid)));
  }

  public void remove(String fileid)
  {
    this.gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileid)));
  }

  public String save(MultipartFile multipartFile, Map<String, Object> map)
  {
    String fileId = null;
    if (multipartFile.isEmpty()) {
      this.logger.info(" Upload file is empty ");
      return fileId;
    }
    try
    {
      String contentType = multipartFile.getContentType();

      String originalName = multipartFile.getName();

      DBObject metadata = new BasicDBObject();
      metadata.putAll(map);
      metadata.put("createTime", new Date());
      metadata.put("name", originalName);
      GridFSFile gridFSFile = this.gridFsTemplate.store(multipartFile.getInputStream(), originalName, contentType, metadata);

      fileId = gridFSFile.getId().toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileId;
  }

  public String save(String string, String businessKey, String userId, MultipartFile multipartFile, String tenantId)
  {
    String fileId = null;
    if (multipartFile.isEmpty()) {
      this.logger.info(" Upload file is empty   type:{} , businessKey:{} ,userId:{}", new Object[] { string, businessKey, userId });
      return fileId;
    }
    try
    {
      String contentType = multipartFile.getContentType();

      String originalName = multipartFile.getName();

      DBObject metadata = new BasicDBObject();
      metadata.put("tenantId", tenantId);
      metadata.put("createTime", new Date());
      metadata.put("userId", userId);
      metadata.put("businessKey", businessKey);
      metadata.put("type", string);
      metadata.put("name", originalName);
      GridFSFile gridFSFile = this.gridFsTemplate.store(multipartFile.getInputStream(), originalName, contentType, metadata);

      fileId = gridFSFile.getId().toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileId;
  }
}