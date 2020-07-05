package com.tensquare.repository;

import com.tensquare.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<Article,String>{

    Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);
}
