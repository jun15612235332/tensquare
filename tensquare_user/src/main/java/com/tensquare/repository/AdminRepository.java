package com.tensquare.repository;

import com.tensquare.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{
    Admin findByLoginname(String loginname);
}
