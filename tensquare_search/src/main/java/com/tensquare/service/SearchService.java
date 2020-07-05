package com.tensquare.service;

import com.tensquare.model.Article;
import com.tensquare.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    public void save(Article article) {
        searchRepository.save(article);
    }

    public Page<Article> findByKey(String key, int pageNo, int size) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        return searchRepository.findByTitleOrContent(key,key,pageable);
    }
}
