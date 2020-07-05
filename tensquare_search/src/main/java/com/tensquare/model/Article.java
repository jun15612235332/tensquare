package com.tensquare.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;
// @Document声明es的文档类型
// indexName es的所索引库名字
// type  es的类型[表]
@Document(indexName = "tensquare_article",type = "article")
@Data
public class Article implements Serializable{
    @Id
    private String id;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    // 状态用于当mysql中删除数据时es更新不及时,做查询判断用
    private String state;//审核状态
}
