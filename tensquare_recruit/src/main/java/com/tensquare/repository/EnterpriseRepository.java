package com.tensquare.repository;

import com.tensquare.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseRepository extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{
    List<Enterprise> findByIshot(String s);
}
