package com.citsh.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.status.ErrorStatus;
import java.util.Date;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent>
{
  protected void append(ILoggingEvent eventObject)
  {
    MongoTemplate mongoTemplate = (MongoTemplate)ApplicationContextProvider.getBean(MongoTemplate.class);
    try {
      Document document = getDocument(eventObject.getFormattedMessage());
      if (document != null) {
        document.append("date", new Date(eventObject.getTimeStamp()));
        document.append("logger", eventObject.getLoggerName());
        document.append("thread", eventObject.getThreadName());
        document.append("level", eventObject.getLevel().toString());

        mongoTemplate.insert(document, "log");
      }
    } catch (Exception e) {
      addStatus(new ErrorStatus("日志写入到MongDB出错", this, e));
    }
  }

  private Document getDocument(String json)
  {
    try {
      if (json.contains("mongomsg")) {
        return Document.parse(json);
      }
      return null; } catch (Exception e) {
    }
    return null;
  }
}