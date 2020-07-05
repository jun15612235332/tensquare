package com.tensquare.controller;

import com.tensquare.model.Reply;
import com.tensquare.service.ReplyService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    //全部列表
    @GetMapping
    public ResultObject findAll() {
        List<Reply> replies = replyService.findAll();
        return new ResultObject(true, StatusCode.OK, "成功", replies);
    }
    //根据ID查询回答
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id) {
        Reply reply = replyService.findById(id);
        return new ResultObject(true, StatusCode.OK, "成功", reply);
    }
    //修改回答
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Reply reply){
        reply.setId(id);
        replyService.updateById(reply);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据ID删除回答
    @DeleteMapping("/{id}")
    public ResultObject deleteById(@PathVariable String id){
        replyService.deleteById(id);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //增加回答
    @PostMapping
    public ResultObject insert(@RequestBody Reply reply){
        replyService.insert(reply);
        return new ResultObject(true, StatusCode.OK,"添加成功");
    }
    //回答分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Reply reply){
        Page<Reply> page=replyService.searchPageSize(pageNo,size,reply);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Reply>(page.getTotalElements(),page.getContent()));
    }
    //根据问题ID查询回答列表
    @GetMapping("/problem/{problemId}")
    public ResultObject findByProblemid(@PathVariable String problemId){
        List<Reply> replies=replyService.findByProblemid(problemId);
        return new ResultObject(true,StatusCode.OK,"查询成功",replies);
    }
    //回答问题
//    @PostMapping
//    public ResultObject insertOne(@RequestBody Reply reply){
//        replyService.insert(reply);
//        return new ResultObject(true, StatusCode.OK,"添加成功");
//    }
    //我的回答列表(待完成)
//    @GetMapping("/list/{pageNo}/{size}")
//    public ResultObject list(@PathVariable Integer pageNo,@PathVariable Integer size){
//
//        return new ResultObject();
//    }
}
