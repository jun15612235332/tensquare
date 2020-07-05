package com.tensquare.repository;

import com.tensquare.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelRepository extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{
}
