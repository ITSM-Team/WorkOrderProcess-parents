package com.citsh.humantask;
public class TaskUserDTO
{
  private String catalog;
  private String type;
  private String value;
  private String status;

  public String getCatalog()
  {
    return this.catalog;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
