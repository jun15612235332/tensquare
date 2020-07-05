package com.tensquare.repository;

import com.tensquare.model.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemRepository extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    @Query(value = "select * from tb_pl pl,tb_problem pro where pro.id=pl.problemid and pl.labelid=? order by replytime desc ",nativeQuery = true)
    Page<Problem> searchNewlist(String labelid, Pageable pageable);

    @Query(value = "select * from tb_pl pl,tb_problem pro where pro.id=pl.problemid and pl.labelid=? order by reply desc ",nativeQuery = true)
    Page<Problem> searchHotlist(String labelid, Pageable pageable);

    @Query(value = "select * from tb_pl pl,tb_problem pro where pro.id=pl.problemid and pl.labelid=? and reply=0 order by createtime desc ",nativeQuery = true)
    Page<Problem> searchWaitlist(String labelid, Pageable pageable);

    @Query(value = "select * from tb_pl pl,tb_problem pro where pro.id=pl.problemid and pl.labelid=? ",nativeQuery = true)
    Page<Problem> all(String labelid, Pageable pageable);
}
