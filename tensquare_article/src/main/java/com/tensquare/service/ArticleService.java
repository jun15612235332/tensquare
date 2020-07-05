package com.tensquare.service;

import com.tensquare.model.Article;
import com.tensquare.repository.ArticleRepository;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Article> findAll() {
        //删除缓存
//        Set keys = redisTemplate.keys("article");
//        redisTemplate.delete(keys);
        ListOperations opsForList = redisTemplate.opsForList();
        List<Article> articles = opsForList.range("article", 0, 10);
        System.out.println("redis中查到的数据个数:"+articles.size());
        if (articles.size()==0){
            System.out.println("从数据库中查询数据");
            articles = articleRepository.findAll();
            //把数据放入redis中
            for (Article article:articles) {
                opsForList.rightPush("article",article);
            }
        }
        return articles;
    }

    public Article findById(String id) {
        Article article = (Article) redisTemplate.opsForValue().get("article:" + id);
        System.out.println("article====="+article);
        //如果没有值就去数据库查
        if (article == null){
            //查数据库
            System.out.println("findById查数据库");
            article = articleRepository.findById(id).get();
            redisTemplate.opsForValue().set("article:"+id,article);
        }
        return article;
    }

    public void add(Article article) {
        article.setId(idWorker.nextId()+"");
        articleRepository.save(article);
        //把数据添加进redis缓存一份
        redisTemplate.opsForList().rightPush("article",article);
        redisTemplate.opsForValue().set("article:"+article.getId(),article);
    }

    public void updateById(Article article) {
        articleRepository.save(article);
        //删除redis中相应的数据
        Set keys = redisTemplate.keys("article");
        redisTemplate.delete(keys);
        redisTemplate.delete("article:"+article.getId());
        //把修改好的数据插入redis中
        redisTemplate.opsForValue().set("article:"+article.getId(),article);
    }

    public void deleteById(String id) {
        articleRepository.deleteById(id);
        //删除redis中相应的数据
        Set keys = redisTemplate.keys("article");
        redisTemplate.delete(keys);
        redisTemplate.delete("article:"+id);
    }

    public List<Article> search(Article article) {
        List<Article> articles = articleRepository.findAll(new Specification<Article>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (article.getTitle() != null && !"".equals(article.getTitle())) {
                    predicate = criteriaBuilder.like(root.get("title").as(String.class), "%" + article.getTitle() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return articles;
    }

    public Page<Article> searchPageSize(Integer pageNo, Integer size, Article article) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Article> page = articleRepository.findAll(new Specification<Article>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (article.getTitle() != null && !"".equals(article.getTitle())) {
                    predicate = criteriaBuilder.like(root.get("title").as(String.class), "%" + article.getTitle() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public void examine(String id) {
        articleRepository.examine(id);
    }

    public void thumbup(String id) {
        articleRepository.thumbup(id);
    }


    public Page<Article> searchChannel(Integer pageNo, Integer size, String channelId) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return articleRepository.searchChannel(channelId,pageable);
    }

    public Page<Article> searchColumn(Integer pageNo, Integer size, String columnId) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return articleRepository.searchColumn(columnId,pageable);
    }

    public List<Article> top() {
        return articleRepository.findByIstopLike("1");
    }

    public void updateThumbupById(String articleId, int size) {
        articleRepository.updateThumbupById(articleId,size);
    }

    public List<Article> selectByIds(List<String> articleIds) {
        return articleRepository.selectByIds(articleIds);
    }
}
