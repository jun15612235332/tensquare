package com.tensquare.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user_like_article")
@Data
public class UserLikeArticle {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "article_id")
    private String articleId;
}
