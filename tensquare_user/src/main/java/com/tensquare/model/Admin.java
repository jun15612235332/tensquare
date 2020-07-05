package com.tensquare.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_admin")
public class Admin {
    @Id
    private String id;
    private String loginname;
    private String password;
    private String state;
}
