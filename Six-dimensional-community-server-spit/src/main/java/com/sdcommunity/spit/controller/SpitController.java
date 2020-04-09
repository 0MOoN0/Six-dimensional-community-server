package com.sdcommunity.spit.controller;

import com.sdcommunity.spit.pojo.Spit;
import com.sdcommunity.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * @author Leon
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@PathVariable int page,
                             @PathVariable int size,
                             @RequestBody Map searchMap){
        Page<Spit> pageData = spitService.findSearch(page, size, searchMap);
        return new Result(true, StatusCode.OK.getCode(), StatusCode.OK.getMsg(),
                new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", spitService.findAll());
    }

    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", spitService.findById(spitId));
    }

    @PostMapping
    public Result save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK.getCode(), "保存成功");
    }

    @PutMapping("/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        Optional.ofNullable(spitId).ifPresent(spit::set_id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK.getCode(), "修改成功");
    }

    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK.getCode(), "删除成功");
    }

    //根据上级ID查询吐槽分页数据
    @RequestMapping(value = "/commentlist/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParenid(@PathVariable String parentId,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true,StatusCode.OK.getCode(),"查询成功",new PageResult<>(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 点赞操作，使用Redis判断是否已经点赞过
     * @param id
     * @return
     */
    @PutMapping("/thumbup/{id}")
    // TODO: 20191216 Leon 修改为取消点赞功能，并做数据校验，避免出现负赞
    public Result updateThumbup(@PathVariable String id){
        // 模拟用户ID
        String userId = "123";
        if(redisTemplate.opsForValue().get("thumbup_"+userId+"_"+id) != null){
            // 已经点赞过
            return new Result(true, StatusCode.REPERROR.getCode(), "你已经点过赞了");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_"+userId+"_"+id, 1);
        return new Result(true,StatusCode.OK.getCode(),"点赞成功");
    }

    @GetMapping("/thumbup/{spitid}")
    public Result isThumbup(@PathVariable String spitid){
        // 模拟用户ID
        String userid = "123";
        if(redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitid) != null){
            // 已经点赞过
            return new Result(true, StatusCode.OK.getCode(),StatusCode.OK.getMsg(),1);
        }else{
            return new Result(true, StatusCode.OK.getCode(),StatusCode.OK.getMsg(),0);
        }
    }
}
