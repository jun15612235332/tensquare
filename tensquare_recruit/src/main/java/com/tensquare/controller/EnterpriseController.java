package com.tensquare.controller;

import com.tensquare.model.Enterprise;
import com.tensquare.service.EnterpriseService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    //企业全部列表
    @GetMapping
    public ResultObject findAll() {
        List<Enterprise> enterprises = enterpriseService.findAll();
        return new ResultObject(true, StatusCode.OK, "成功", enterprises);
    }

    //根据id查询企业
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id) {
        Enterprise enterprise = enterpriseService.findById(id);
        return new ResultObject(true, StatusCode.OK, "成功", enterprise);
    }

    //增加企业
    @PostMapping
    public ResultObject insert(@RequestBody Enterprise enterprise) {
        enterpriseService.insert(enterprise);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //修改企业
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Enterprise enterprise){
        enterprise.setId(id);
        enterpriseService.updateById(enterprise);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据id删除企业
    @DeleteMapping("/{id}")
    public ResultObject deleteById(@PathVariable String id){
        enterpriseService.deleteById(id);
        return new ResultObject(true, StatusCode.OK, "成功");
    }
    //根据条件查询企业列表
    @PostMapping("/search")
    public ResultObject search(@RequestBody Enterprise enterprise){
        List<Enterprise> enterprises=enterpriseService.search(enterprise);
        return new ResultObject(true, StatusCode.OK, "成功", enterprises);
    }
    //分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Enterprise enterprise){
        Page<Enterprise> page=enterpriseService.searchPageSize(pageNo,size,enterprise);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Enterprise>(page.getTotalElements(),page.getContent()));
    }
    //热门企业列表
    @GetMapping("/search/hotlist")
    public ResultObject searchHotlist(){
        List<Enterprise> enterprises=enterpriseService.searchHotlist("1");
        return new ResultObject(true, StatusCode.OK, "成功", enterprises);
    }
}
