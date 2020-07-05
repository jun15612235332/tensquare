package com.tensquare.controller;

import com.tensquare.service.SpitService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import com.tensquare.model.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

    //Spit全部列表
    @GetMapping
    public ResultObject findAll(){
        List<Spit> spits=spitService.findAll();
        return new ResultObject(true, StatusCode.OK,"查询成功",spits);
    }
    //增加吐槽
    @PostMapping
    public ResultObject insert(@RequestBody Spit spit){
        spitService.insert(spit);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //根据id查询吐槽
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id){
        Spit spit=spitService.findById(id);
        return new ResultObject(true,StatusCode.OK,"查询成功",spit);
    }
    //修改吐槽
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.updateById(spit);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //根据id删除吐槽
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        spitService.delete(id);
        return new ResultObject(true,StatusCode.OK,"删除成功");
    }
    //吐槽点赞
    @PutMapping("/thumbup/{id}")
    public ResultObject thumbup(@PathVariable String id){
        spitService.thumbup(id);
        return new ResultObject(true,StatusCode.OK,"点赞成功");
    }

    //根据条件查询
    @GetMapping("/search")
    public ResultObject search(@RequestBody Spit spit){
        List<Spit> spits=spitService.search(spit);
        return new ResultObject(true,StatusCode.OK,"查询成功",spits);
    }
    //根据上级ID查询吐槽数据（分页）
    @GetMapping("/comment/{parentid}/{pageNo}/{size}")
    public ResultObject comment(@PathVariable String parentid,@PathVariable Integer pageNo,@PathVariable Integer size){
        Page<Spit> page=spitService.comment(parentid,pageNo,size);
        return new ResultObject(true,StatusCode.OK,"查询成功",new PageResult<Spit>(page.getTotalElements(),page.getContent()));
    }
}
