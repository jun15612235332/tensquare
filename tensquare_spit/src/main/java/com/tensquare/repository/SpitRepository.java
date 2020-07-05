package com.tensquare.repository;

import com.tensquare.model.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpitRepository extends MongoRepository<Spit,String>{

    Page<Spit> findByParentid(String parentid, Pageable pageable);

    List<Spit> findBy_idLikeAndContentLike(String id, String content);
}
