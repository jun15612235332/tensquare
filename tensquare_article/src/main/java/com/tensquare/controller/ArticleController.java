package com.tensquare.controller;

import com.tensquare.model.Article;
import com.tensquare.service.ArticleService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //文章全部列表
    @GetMapping
    public ResultObject findAll(){
        List<Article> articles=articleService.findAll();
        return new ResultObject(true, StatusCode.OK,"成功",articles);
    }
    //根据id查询文章
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id){
        Article article=articleService.findById(id);
        return new ResultObject(true,StatusCode.OK,"成功",article);
    }
    //增加文章
    @PostMapping
    public ResultObject insert(@RequestBody Article article){
        articleService.add(article);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //修改文章
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Article article){
        article.setId(id);
        articleService.updateById(article);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //根据id删除文章
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        articleService.deleteById(id);
        return new ResultObject(true,StatusCode.OK,"删除成功");
    }
    //根据条件查询文章
    @PostMapping("/search")
    public ResultObject search(@RequestBody Article article){
        List<Article> articles=articleService.search(article);
        return new ResultObject(true, StatusCode.OK, "成功", articles);
    }
    //文章分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Article article){
        Page<Article> page=articleService.searchPageSize(pageNo,size,article);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Article>(page.getTotalElements(),page.getContent()));
    }
    //文章审核
    @PutMapping("/examine/{id}")
    public ResultObject examine(@PathVariable String id){
        articleService.examine(id);
        return new ResultObject(true,StatusCode.OK,"操作成功");
    }
    //点赞
    @PutMapping("/thumbup/{id}")
    public ResultObject thumbup(@PathVariable String id){
        articleService.thumbup(id);
        return new ResultObject(true,StatusCode.OK,"操作成功");
    }
    //根据频道id获取文章列表
    @PostMapping("/channel/{channelId}/{pageNo}/{size}")
    public ResultObject searchChannel(@PathVariable String channelId,@PathVariable Integer pageNo,@PathVariable Integer size){
        Page<Article> page=articleService.searchChannel(pageNo,size,channelId);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Article>(page.getTotalElements(),page.getContent()));
    }
    //根据专栏id获取文章列表
    @PostMapping("/column/{columnId}/{pageNo}/{size}")
    public ResultObject searchColumn(@PathVariable String columnId,@PathVariable Integer pageNo,@PathVariable Integer size){
        Page<Article> page=articleService.searchColumn(pageNo,size,columnId);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Article>(page.getTotalElements(),page.getContent()));
    }
    //头条文章
    @GetMapping("/top")
    public ResultObject top(){
        List<Article> articles=articleService.top();
        return new ResultObject(true,StatusCode.OK,"成功查询到数据",articles);
    }

}
