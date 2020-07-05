package com.tensquare.repository;

import com.tensquare.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply> {

    List<Reply> findByProblemid(String problemId);
}
