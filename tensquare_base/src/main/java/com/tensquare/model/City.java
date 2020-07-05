package com.tensquare.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_city")
@Data
public class City {
    @Id
    private String id;
    private String name;
    private String ishot;

}
