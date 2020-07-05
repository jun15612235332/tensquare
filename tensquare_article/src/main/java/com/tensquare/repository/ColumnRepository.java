package com.tensquare.repository;

import com.tensquare.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{

    List<Column> findByUserid(String userId);

    @Modifying
    @Query(value = "UPDATE tb_column set checktime=NOW(),state='1' WHERE id=?",nativeQuery = true)
    void examine(String columnId);
}
