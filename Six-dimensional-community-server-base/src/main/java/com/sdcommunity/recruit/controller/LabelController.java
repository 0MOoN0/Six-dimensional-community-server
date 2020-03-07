package com.sdcommunity.recruit.controller;

import com.sdcommunity.recruit.pojo.Label;
import com.sdcommunity.recruit.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin            // 跨域
@RequestMapping("/label")
@RefreshScope
public class LabelController {

    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", labelService.findAll());
    }

    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String id) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", labelService.findById(id));
    }

    @PostMapping
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK.getCode(), "添加成功");
    }

    @PutMapping("/{labelId}")
    public Result update(@PathVariable("labelId") String id, @RequestBody Label label) {
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK.getCode(), "修改成功");
    }

    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable("labelId") String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK.getCode(), "删除成功");
    }

    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK.getCode(), "查询成功", list);
    }

    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label, @PathVariable("page") int currentPage, @PathVariable("size") int pageSize) {
        Page<Label> pageData = labelService.pageQuery(label, currentPage, pageSize);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    @GetMapping("/toplist")
    public Result hotList(){
        return new Result(true,StatusCode.OK.getCode(),StatusCode.OK.getMsg(), labelService.hotlist());
    }

}
