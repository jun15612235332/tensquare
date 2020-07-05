package com.tensquare.repository;

import com.tensquare.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article> {
    @Modifying
    @Query(value = "UPDATE tb_article SET state=1 WHERE id = ?", nativeQuery = true)
    void examine(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup=thumbup+1 WHERE id = ?", nativeQuery = true)
    void thumbup(String id);

    @Query(value = "select * from tb_article where channelid=?",nativeQuery = true)
    Page<Article> searchChannel(String channelId, Pageable pageable);

    @Query(value = "select * from tb_article where columnid=?",nativeQuery = true)
    Page<Article> searchColumn(String columnId, Pageable pageable);

    List<Article> findByIstopLike(String s);

    @Modifying
    @Query(value = "update tb_article set thumbup=?2 where id=?1",nativeQuery = true)
    void updateThumbupById(String articleId, int size);

    @Query(value = "select * from tb_article where id in (@ids)",nativeQuery = true)
    List<Article> selectByIds(@Param("ids") List<String> articleIds);
}
