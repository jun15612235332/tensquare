package com.tensquare.service;

import com.tensquare.model.UserLikeArticle;
import com.tensquare.repository.UserLikeArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserLikeArticleService {
    @Autowired
    UserLikeArticleRepository userLikeArticleRepository;

    public UserLikeArticle findByUserIdArticleId(String userId, String articleId) {
        return userLikeArticleRepository.findByUserIdArticleId(userId,articleId);
    }

    public void save(UserLikeArticle userLikeArticle) {
        userLikeArticleRepository.save(userLikeArticle);
    }

    public void deleteByArticleId(String articleId) {
        userLikeArticleRepository.deleteByArticleId(articleId);
    }
}
