package com.tensquare.controller;

import com.tensquare.model.Recruit;
import com.tensquare.service.RecruitService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;


    //招聘全部列表
    @GetMapping
    public ResultObject findAll() {
        List<Recruit> recruits = recruitService.findAll();
        return new ResultObject(true, StatusCode.OK, "成功", recruits);
    }
    //根据id查询招聘
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id) {
        Recruit recruit = recruitService.findById(id);
        return new ResultObject(true, StatusCode.OK, "成功", recruit);
    }
    //增加招聘
    @PostMapping
    public ResultObject insert(@RequestBody Recruit recruit){
        recruitService.insert(recruit);
        return new ResultObject(true, StatusCode.OK,"添加成功");
    }
    //修改招聘
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Recruit recruit){
        recruit.setId(id);
        recruitService.updateById(recruit);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据id删除招聘
    @DeleteMapping("/{id}")
    public ResultObject deleteById(@PathVariable String id){
        recruitService.deleteById(id);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据条件查询招聘列表
    @PostMapping("/search")
    public ResultObject search(@RequestBody Recruit recruit){
        List<Recruit> recruits=recruitService.search(recruit);
        return new ResultObject(true, StatusCode.OK, "成功", recruits);
    }
    //分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Recruit recruit){
        Page<Recruit> page=recruitService.searchPageSize(pageNo,size,recruit);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Recruit>(page.getTotalElements(),page.getContent()));
    }
    //推荐职位
    @GetMapping("/search/recommend")
    public ResultObject searchRecommend(){
        List<Recruit> recruits=recruitService.searchRecommend();
        return new ResultObject(true,StatusCode.OK,"成功",recruits);
    }
    //最新职位
    @GetMapping("/search/newlist")
    public ResultObject searchNewlist(){
        List<Recruit> recruits=recruitService.searchNewlist();
        return new ResultObject(true,StatusCode.OK,"成功",recruits);
    }
}
