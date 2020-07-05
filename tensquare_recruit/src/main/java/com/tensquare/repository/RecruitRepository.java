package com.tensquare.repository;

import com.tensquare.model.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    List<Recruit> findTop2ByStateOrderByCreatetimeDesc(String state);

    List<Recruit> findTop2ByStateNotOrderByCreatetimeDesc(String state);
}
