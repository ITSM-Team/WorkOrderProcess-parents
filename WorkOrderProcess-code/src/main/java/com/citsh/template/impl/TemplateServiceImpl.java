package com.citsh.template.impl;

import com.citsh.template.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateServiceImpl
  implements TemplateService
{
  private static Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);
  private String baseDir;
  private String encoding = "UTF-8";
  private Configuration configuration;

  @PostConstruct
  public void init()
  {
    this.configuration = new Configuration(Configuration.VERSION_2_3_21);
    File templateDir = new File(this.baseDir);
    templateDir.mkdirs();
    try {
      this.configuration.setDirectoryForTemplateLoading(templateDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String renderText(String text, Map<String, Object> data)
  {
    if (text == null) {
      logger.warn("text is null");
      return "";
    }
    try {
      Template template = new Template(text, text, this.configuration);
      StringWriter writer = new StringWriter();
      template.process(template, writer);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String render(String templatePath, Map<String, Object> data) {
    try {
      Template template = this.configuration.getTemplate(templatePath, this.encoding);
      StringWriter writer = new StringWriter();
      template.process(data, writer);
      return writer.toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public void renderTo(String templatePath, Map<String, Object> data, File targetFile) {
    try {
      Template template = this.configuration.getTemplate(templatePath, this.encoding);
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile), this.encoding));
      template.process(data, writer);
      writer.flush();
      writer.close();
    } catch (TemplateException ex) {
      logger.error(ex.getMessage(), ex);
    } catch (IOException ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

  public void setBaseDir(String baseDir)
  {
    this.baseDir = baseDir;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
}