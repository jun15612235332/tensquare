package com.tensquare.controller;

import com.tensquare.model.Column;
import com.tensquare.service.ColumnService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    //专栏全部列表
    @GetMapping
    public ResultObject findAll(){
        List<Column> columns=columnService.findAll();
        return new ResultObject(true, StatusCode.OK,"成功",columns);
    }
    //根据id查询专栏
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id){
        Column column=columnService.findById(id);
        return new ResultObject(true,StatusCode.OK,"成功",column);
    }
    //增加专栏
    @PostMapping
    public ResultObject insert(@RequestBody Column column){
        columnService.add(column);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //修改专栏
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Column column){
        column.setId(id);
        columnService.updateById(column);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //根据id删除专栏
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        columnService.deleteById(id);
        return new ResultObject(true,StatusCode.OK,"删除成功");
    }
    //根据条件查询专栏
    @PostMapping("/search")
    public ResultObject search(@RequestBody Column column){
        List<Column> columns=columnService.search(column);
        return new ResultObject(true, StatusCode.OK, "成功", columns);
    }
    //专栏分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Column column){
        Page<Column> page=columnService.searchPageSize(pageNo,size,column);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Column>(page.getTotalElements(),page.getContent()));
    }
    //根据用户id查询专栏列表
    @GetMapping("/user/{userId}")
    public ResultObject findByUserid(@PathVariable String userId){
        List<Column> columns=columnService.findByUserid(userId);
        return new ResultObject(true,StatusCode.OK,"查询成功",columns);
    }
    //专栏申请
    @PostMapping("/apply")
    public ResultObject apply(@RequestBody Column column){
        columnService.apply(column);
        return new ResultObject(true,StatusCode.OK,"申请成功");
    }
    //申请审核
    @PutMapping("/examine/{columnId}")
    public ResultObject examine(@PathVariable String columnId){
        columnService.examine(columnId);
        return new ResultObject(true,StatusCode.OK,"申请成功");
    }

}
