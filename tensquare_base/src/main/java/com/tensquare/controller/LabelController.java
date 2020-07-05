package com.tensquare.controller;

import com.tensquare.model.Label;
import com.tensquare.service.LabelService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    //标签全部列表
    @GetMapping
    public ResultObject findAll(){
        System.out.println("请求进来了");
        List<Label> labels=labelService.findAll();
        return new ResultObject(true, StatusCode.OK,"查询成功",labels);
    }
    //增加标签
    @PostMapping
    public ResultObject insert(@RequestBody Label label){
        labelService.insert(label);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //根据id查询
    @GetMapping("/{id}")
    public ResultObject findLabelById(@PathVariable String id){
        Label label=labelService.findLabelById(id);
        return new ResultObject(true,StatusCode.OK,"操作成功",label);
    }
    //修改标签
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        labelService.updateById(label);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //根据id删除标签
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        labelService.delete(id);
        return new ResultObject(true,StatusCode.OK,"操作成功");
    }
    //模糊查询
    @PostMapping("/search")
    public ResultObject findAllBySearch(@RequestBody Label label){
        List<Label> labels=labelService.findAllBySearch(label);
        return new ResultObject(true,StatusCode.OK,"查询成功",labels);
    }
    //模糊+分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchAddPage(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Label label){
        Page<Label> page=labelService.searchAddPage(pageNo,size,label);
        return new ResultObject(true,StatusCode.OK,"查询成功",new PageResult<Label>(page.getTotalElements(),page.getContent()));
    }

}
