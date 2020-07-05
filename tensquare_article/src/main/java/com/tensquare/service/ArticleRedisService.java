package com.tensquare.service;


import java.util.List;

public interface ArticleRedisService {
    void like(String articleId, String likedUserId, String likePoseId);

    void notLike(String articleId, String likedUserId, String likePoseId);

    List<String> userLike(String likedPoseId);

    String userTotal(String likedPoseId);

    String articleTotal(String articleId);

    int getHits(String addr);
}
