package com.tensquare.repository;

import com.tensquare.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleRedisRepository extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
}
