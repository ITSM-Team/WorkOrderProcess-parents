package com.citsh.file;

import com.mongodb.gridfs.GridFSFile;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public abstract interface GridFS
{
  public abstract String saveFile(String paramString1, String paramString2, Long paramLong, File paramFile, String paramString3);

  public abstract GridFSFile getFile(String paramString);

  public abstract void remove(String paramString);

  public abstract String save(String paramString1, String paramString2, String paramString3, MultipartFile paramMultipartFile, String paramString4);
}