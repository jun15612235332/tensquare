package com.tensquare.controller;

import com.tensquare.model.City;
import com.tensquare.service.CityService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    //返回城市列表
    @GetMapping
    public ResultObject findAll(){
        List<City> cities=cityService.findAll();
        return new ResultObject(true, StatusCode.OK,"成功",cities);
    }
    //新增城市
    @PostMapping
    public ResultObject insert(@RequestBody City city){
        cityService.add(city);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //修改城市
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody City city){
        city.setId(id);
        cityService.updateById(city);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //删除城市
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        cityService.deleteById(id);
        return new ResultObject(true,StatusCode.OK,"删除成功");
    }
    //根据id查询城市
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id){
        City city=cityService.findById(id);
        return new ResultObject(true,StatusCode.OK,"成功",city);
    }
    //根据条件查询城市列表
    @PostMapping("/search")
    public ResultObject search(@RequestBody City city){
        List<City> cities=cityService.search(city);
        return new ResultObject(true, StatusCode.OK, "成功", cities);
    }
    //根据条件查询城市列表
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody City city){
        Page<City> page=cityService.searchPageSize(pageNo,size,city);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<City>(page.getTotalElements(),page.getContent()));
    }
}
