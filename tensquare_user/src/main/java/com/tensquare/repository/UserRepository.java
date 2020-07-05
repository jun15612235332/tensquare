package com.tensquare.repository;

import com.tensquare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String>{
    User findByMobile(String mobile);
}
