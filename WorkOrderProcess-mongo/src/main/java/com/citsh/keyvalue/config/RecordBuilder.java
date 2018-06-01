package com.citsh.keyvalue.config;

import com.citsh.file.GridFS;
import com.citsh.handler.MultipartHandler;
import com.citsh.keyvalue.FormParameter;
import com.citsh.keyvalue.entity.Prop;
import com.citsh.keyvalue.entity.Record;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public class RecordBuilder
{
  private static Logger logger = LoggerFactory.getLogger(RecordBuilder.class);

  public Record build(Record record, int status, FormParameter formParameter)
  {
    record.setStatus(status);

    for (Map.Entry entry : formParameter.getMultiValueMap().entrySet()) {
      String key = (String)entry.getKey();
      List value = (List)entry.getValue();

      if ((value == null) || (value.isEmpty()))
      {
        continue;
      }
      Prop prop = new Prop();
      prop.setCode(key);
      prop.setType(0);
      prop.setValue(getValue(value));
      record.getProps().put(prop.getCode(), prop);
    }

    return record;
  }

  public Record build(String category, int status, FormParameter formParameter, String userId, String tenantId)
  {
    Record record = new Record();
    record.setCategory(category);
    record.setUserId(userId);
    record.setCreateTime(new Date());
    record.setTenantId(tenantId);

    return build(record, status, formParameter);
  }

  public Record build(Record record, int status, String ref)
  {
    if (record == null) {
      record = new Record();
    }

    record.setRef(ref);
    record.setStatus(status);

    return record;
  }

  public Record build(Record record, MultiValueMap<String, String> multiValueMap, String tenantId) throws Exception {
    for (Map.Entry entry : multiValueMap.entrySet()) {
      String key = (String)entry.getKey();

      if (key == null)
      {
        continue;
      }
      List value = (List)entry.getValue();

      if ((value == null) || (value.isEmpty()))
      {
        continue;
      }
      Prop prop = new Prop();
      prop.setCode(key);
      prop.setType(0);
      prop.setValue(getValue(value));
      record.getProps().put(prop.getCode(), prop);
    }

    return record;
  }

  public String getValue(List<String> values)
  {
    if ((values == null) || (values.isEmpty())) {
      return "";
    }

    if (values.size() == 1) {
      return (String)values.get(0);
    }

    StringBuilder buff = new StringBuilder();

    for (String value : values) {
      buff.append(value).append(",");
    }

    buff.deleteCharAt(buff.length() - 1);

    return buff.toString();
  }

  public Record build(Record record, MultipartHandler multipartHandler, GridFS gridFS, String tenantId) throws Exception
  {
    for (Map.Entry entry : multipartHandler.getMultiValueMap().entrySet()) {
      String key = (String)entry.getKey();

      if (key == null)
      {
        continue;
      }
      List value = (List)entry.getValue();

      if ((value == null) || (value.isEmpty()))
      {
        continue;
      }
      Prop prop = new Prop();
      prop.setCode(key);
      prop.setType(0);
      prop.setValue(getValue(value));
      record.getProps().put(prop.getCode(), prop);
    }

    if (multipartHandler.getMultiFileMap() == null) {
      return record;
    }

    for (Map.Entry entry : multipartHandler.getMultiFileMap().entrySet()) {
      String key = (String)entry.getKey();

      if (key == null)
      {
        continue;
      }
      List value = (List)entry.getValue();

      if ((value == null) || (value.isEmpty()))
      {
        continue;
      }
      MultipartFile multipartFile = (MultipartFile)value.get(0);

      if ((multipartFile.getName() == null) || ("".equals(multipartFile.getName().trim())))
      {
        continue;
      }
      if (multipartFile.getSize() == 0L) {
        logger.info("ignore empty file");

        continue;
      }

      Prop prop = new Prop();
      prop.setCode(key);
      prop.setType(0);
      String fileId = gridFS.save("form", record.getBusinessKey(), record.getUserId(), multipartFile, tenantId);
      logger.info(" File Upload fileId:{}", fileId);
      prop.setValue(fileId);
      record.getProps().put(prop.getCode(), prop);
    }

    return record;
  }
}