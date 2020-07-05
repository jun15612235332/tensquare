package com.tensquare.controller;

import com.tensquare.model.Problem;
import com.tensquare.service.ProblemService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    //全部列表
    @GetMapping
    public ResultObject findAll() {
        List<Problem> problems = problemService.findAll();
        return new ResultObject(true, StatusCode.OK, "成功", problems);
    }
    //根据ID查询问题
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id) {
        Problem problem = problemService.findById(id);
        return new ResultObject(true, StatusCode.OK, "成功", problem);
    }
    //修改问题
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Problem problem){
        problem.setId(id);
        problemService.updateById(problem);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据ID删除问题
    @DeleteMapping("/{id}")
    public ResultObject deleteById(@PathVariable String id){
        problemService.deleteById(id);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //增加问题
    @PostMapping
    public ResultObject insert(@RequestBody Problem problem){
        problemService.insert(problem);
        return new ResultObject(true, StatusCode.OK,"添加成功");
    }
    //根据条件查询问题列表
    @PostMapping("/search")
    public ResultObject search(@RequestBody Problem problem){
        List<Problem> problems=problemService.search(problem);
        return new ResultObject(true, StatusCode.OK, "成功", problems);
    }
    //问题分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Problem problem){
        Page<Problem> page=problemService.searchPageSize(pageNo,size,problem);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Problem>(page.getTotalElements(),page.getContent()));
    }
    //最新问答列表
    @GetMapping("/newlist/{labelid}/{pageNo}/{size}")
    public ResultObject searchNewlist(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable String labelid){
        Page<Problem> page=problemService.searchNewlist(pageNo,size,labelid);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Problem>(page.getTotalElements(),page.getContent()));
    }
    //热门问答列表
    @GetMapping("/hotlist/{labelid}/{pageNo}/{size}")
    public ResultObject searchHotlist(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable String labelid){
        Page<Problem> page=problemService.searchHotlist(pageNo,size,labelid);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Problem>(page.getTotalElements(),page.getContent()));
    }
    //等待回答列表
    @GetMapping("/waitlist/{labelid}/{pageNo}/{size}")
    public ResultObject searchWaitlist(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable String labelid){
        Page<Problem> page=problemService.searchWaitlist(pageNo,size,labelid);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Problem>(page.getTotalElements(),page.getContent()));
    }
    //分页
    @PostMapping("/all/{labelid}/{pageNo}/{size}")
    public ResultObject all(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable String labelid){
        Page<Problem> page=problemService.all(pageNo,size,labelid);
        return new ResultObject(true,StatusCode.OK,"成功",new PageResult<Problem>(page.getTotalElements(),page.getContent()));
    }
}
