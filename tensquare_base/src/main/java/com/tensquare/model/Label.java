package com.tensquare.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity //声明这是一个JPA的实体类,与数据库对应
@Table(name = "tb_label")  //与数据库表名保持一致
@Data
public class Label {
    @Id
    private String id;
    private String labelname;
    private String state;
    private Integer count;
    private String recommend;
}
