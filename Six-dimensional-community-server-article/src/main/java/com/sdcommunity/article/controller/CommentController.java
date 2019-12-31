package com.sdcommunity.article.controller;

import com.sdcommunity.article.pojo.Comment;
import com.sdcommunity.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * date: 2018/11/28 11:51
 * author: loveLy
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    //根据文章ID查询评论列表
    @GetMapping("/article/{articleid}")
    public Result findByArticleid(@PathVariable String articleid){
        return new Result(true, StatusCode.OK.getCode(),"查询成功",commentService.findByArticleid(articleid));
    }

    //文章评论新增
    @PostMapping
    public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK.getCode(),"提交成功");
    }
}
