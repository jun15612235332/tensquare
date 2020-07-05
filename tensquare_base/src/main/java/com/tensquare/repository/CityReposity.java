package com.tensquare.repository;

import com.tensquare.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityReposity extends JpaRepository<City,String>,JpaSpecificationExecutor<City>{
}
