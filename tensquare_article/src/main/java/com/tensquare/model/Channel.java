package com.tensquare.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_channel")
public class Channel {
    @Id
    private String id;
    private String name;
    private String state;

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Channel() {
    }

    public Channel(String id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }


}
