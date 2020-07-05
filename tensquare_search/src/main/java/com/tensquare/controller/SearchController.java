package com.tensquare.controller;

import com.tensquare.model.Article;
import com.tensquare.service.SearchService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;



@RestControllerAdvice
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
    @Autowired
    private SearchService searchService;

    //向索引库添加文章
    @PostMapping
    public ResultObject save(@RequestBody Article article){
        searchService.save(article);
        return new ResultObject(true, StatusCode.OK,"添加成功");
    }
    //从索引库查找文章
    @GetMapping("/{key}/{pageNo}/{size}")
    public ResultObject findByKey(@PathVariable String key,
                                  @PathVariable int pageNo,
                                  @PathVariable int size){
        Page<Article> page=searchService.findByKey(key,pageNo,size);
        return new ResultObject(true,StatusCode.OK,"查询成功",new PageResult<Article>(page.getTotalElements(),page.getContent()));
    }
}
