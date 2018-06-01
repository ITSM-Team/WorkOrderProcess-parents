package com.citsh.base.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* TaskConfUser 任务实例配置用户.
*/
@Entity
@Table(name = "TASK_CONF_USER")
public class TaskConfUser implements java.io.Serializable {
 private static final long serialVersionUID = 0L;

 /** 主键. */
 private Long id;

 /** 业务标识. */
 private String businessKey;

 /** 编码. */
 private String code;

 /** 值. */
 private String value;

 public TaskConfUser() {
 }

 public TaskConfUser(Long id) {
     this.id = id;
 }

 public TaskConfUser(Long id, String businessKey, String code, String value) {
     this.id = id;
     this.businessKey = businessKey;
     this.code = code;
     this.value = value;
 }

 /** @return 主键. */
 @Id
 @Column(name = "ID", unique = true, nullable = false)
 public Long getId() {
     return this.id;
 }

 /**
  * @param id
  *            主键.
  */
 public void setId(Long id) {
     this.id = id;
 }

 /** @return 业务标识. */
 @Column(name = "BUSINESS_KEY", length = 200)
 public String getBusinessKey() {
     return this.businessKey;
 }

 /**
  * @param businessKey
  *            业务标识.
  */
 public void setBusinessKey(String businessKey) {
     this.businessKey = businessKey;
 }

 /** @return 编码. */
 @Column(name = "CODE", length = 200)
 public String getCode() {
     return this.code;
 }

 /**
  * @param code
  *            编码.
  */
 public void setCode(String code) {
     this.code = code;
 }

 /** @return 值. */
 @Column(name = "VALUE", length = 200)
 public String getValue() {
     return this.value;
 }

 /**
  * @param value
  *            值.
  */
 public void setValue(String value) {
     this.value = value;
 }
}