package com.citsh.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* TaskDefUser 任务定义用户.
*/
@Entity
@Table(name = "TASK_DEF_USER")
public class TaskDefUser implements java.io.Serializable {
 private static final long serialVersionUID = 0L;

 /** 主键. */
 private Long id;

 /** 外键，任务定义. */
 private TaskDefBase taskDefBase;

 /** 值. */
 private String value;

 /** 类型. */
 private String type;

 /** 分类. */
 private String catalog;

 /** 状态. */
 private String status;

 public TaskDefUser() {
 }

 public TaskDefUser(Long id) {
     this.id = id;
 }

 public TaskDefUser(Long id, TaskDefBase taskDefBase, String value,
         String type, String catalog, String status) {
     this.id = id;
     this.taskDefBase = taskDefBase;
     this.value = value;
     this.type = type;
     this.catalog = catalog;
     this.status = status;
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

 /** @return 外键，任务定义. */
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "BASE_ID")
 public TaskDefBase getTaskDefBase() {
     return this.taskDefBase;
 }

 /**
  * @param taskDefBase
  *            外键，任务定义.
  */
 public void setTaskDefBase(TaskDefBase taskDefBase) {
     this.taskDefBase = taskDefBase;
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

 /** @return 类型. */
 @Column(name = "TYPE", length = 50)
 public String getType() {
     return this.type;
 }

 /**
  * @param type
  *            类型.
  */
 public void setType(String type) {
     this.type = type;
 }

 /** @return 分类. */
 @Column(name = "CATALOG", length = 200)
 public String getCatalog() {
     return this.catalog;
 }

 /**
  * @param catalog
  *            分类.
  */
 public void setCatalog(String catalog) {
     this.catalog = catalog;
 }

 /** @return 状态. */
 @Column(name = "STATUS", length = 50)
 public String getStatus() {
     return this.status;
 }

 /**
  * @param status
  *            状态.
  */
 public void setStatus(String status) {
     this.status = status;
 }
}

