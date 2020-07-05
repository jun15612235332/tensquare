package com.tensquare.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_column")
public class Column {
    @Id
    private String id;
    private String name;
    private String summary;
    private String userid;
    private Date createtime;
    private Date checktime;
    private String state;

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", userid='" + userid + '\'' +
                ", createtime=" + createtime +
                ", checktime=" + checktime +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Column() {
    }

    public Column(String id, String name, String summary, String userid, Date createtime, Date checktime, String state) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.userid = userid;
        this.createtime = createtime;
        this.checktime = checktime;
        this.state = state;
    }
}
