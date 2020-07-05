package com.tensquare.controller;

import com.tensquare.model.Article;
import com.tensquare.service.ArticleRedisService;
import com.tensquare.service.ArticleService;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@RequestMapping("/article")
public class ArticleRedisApplication {
    @Autowired
    ArticleRedisService articleRedisService;
    @Autowired
    ArticleService articleService;

    //点赞
    @PostMapping("/article/{articleId}/{likedUserId}/{likePoseId}")
    public ResultObject like(@PathVariable String articleId,
                             @PathVariable String likedUserId,
                             @PathVariable String likePoseId) {
        articleRedisService.like(articleId,likedUserId,likePoseId);
        return new ResultObject(true, StatusCode.OK,"点赞成功");
    }
    //取消点赞
    @DeleteMapping("/article/{articleId}/{likedUserId}/{likePoseId}")
    public ResultObject notLike(@PathVariable String articleId,
                                @PathVariable String likedUserId,
                                @PathVariable String likePoseId){
        articleRedisService.notLike(articleId,likedUserId,likePoseId);
        return new ResultObject(true, StatusCode.OK,"取消点赞成功");
    }
    //获取用户点赞文章
    @GetMapping("/user/{likedPoseId}/like")
    public ResultObject userLike(@PathVariable String likedPoseId){
        List<String> articleIds=articleRedisService.userLike(likedPoseId);
        List<Article> articles=articleService.selectByIds(articleIds);
        return new ResultObject(true,StatusCode.OK,"查询成功",articles);
    }
    //统计用户总的文章点赞数
    @GetMapping("/user/total/{likedPoseId}")
    public ResultObject userTotal(@PathVariable String likedPoseId){
        String count=articleRedisService.userTotal(likedPoseId);
        return new ResultObject(true,StatusCode.OK,"查询成功",count);
    }
    //统计某篇文章的点赞数
    @GetMapping("/article/total/{articleId}")
    public ResultObject articleTotal(@PathVariable String articleId){
        String count=articleRedisService.articleTotal(articleId);
        return new ResultObject(true,StatusCode.OK,"查询成功",count);
    }
    /**
     * 测试点击量
     */
    @GetMapping("/hits")
    public ResultObject getHits(HttpServletRequest request){
        String addr = request.getRemoteAddr();
        int hits=articleRedisService.getHits(addr);
        return new ResultObject(true,StatusCode.OK,"查询成功",hits);
    }
}
