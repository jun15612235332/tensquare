package com.tensquare.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_user")
@Data
public class User {
    @Id
    private String id;
    private String loginname;
    private String password;
    private String nickname;
    private String sex;
    private Date birthday;
    private String avatar;
    private String mobile;
    private String email;
    private Date regdate;
    private Date updatedate;
    private Date lastdate;
    private Long online;
    private String interest;
    private String personality;
    private Integer fanscount;
    private Integer followcount;
}
