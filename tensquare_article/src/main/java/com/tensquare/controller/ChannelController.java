package com.tensquare.controller;

import com.tensquare.model.Channel;
import com.tensquare.service.ChannelService;
import com.tensquare.util.PageResult;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestControllerAdvice
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    //频道全部列表
    @GetMapping
    public ResultObject findAll(){
        List<Channel> channels=channelService.findAll();
        return new ResultObject(true, StatusCode.OK,"成功",channels);
    }
    //根据id查询频道
    @GetMapping("/{id}")
    public ResultObject findById(@PathVariable String id){
        Channel channel=channelService.findById(id);
        return new ResultObject(true,StatusCode.OK,"成功",channel);
    }
    //增加频道
    @PostMapping
    public ResultObject insert(@RequestBody Channel channel){
        channelService.add(channel);
        return new ResultObject(true,StatusCode.OK,"添加成功");
    }
    //修改频道
    @PutMapping("/{id}")
    public ResultObject updateById(@PathVariable String id,@RequestBody Channel channel){
        channel.setId(id);
        channelService.updateById(channel);
        return new ResultObject(true,StatusCode.OK,"修改成功");
    }
    //根据id删除频道
    @DeleteMapping("/{id}")
    public ResultObject delete(@PathVariable String id){
        channelService.deleteById(id);
        return new ResultObject(true,StatusCode.OK,"删除成功");
    }
    //根据条件查询频道
    @PostMapping("/search")
    public ResultObject search(@RequestBody Channel channel){
        List<Channel> channels=channelService.search(channel);
        return new ResultObject(true, StatusCode.OK, "成功", channels);
    }
    //频道分页
    @PostMapping("/search/{pageNo}/{size}")
    public ResultObject searchPageSize(@PathVariable Integer pageNo,@PathVariable Integer size,@RequestBody Channel channel){
        Page<Channel> page=channelService.searchPageSize(pageNo,size,channel);
        return new ResultObject(true, StatusCode.OK, "成功",new PageResult<Channel>(page.getTotalElements(),page.getContent()));
    }
}
