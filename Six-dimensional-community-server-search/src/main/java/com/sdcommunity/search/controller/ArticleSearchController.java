package com.sdcommunity.search.controller;

import com.sdcommunity.search.pojo.Article;
import com.sdcommunity.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 注意：URL中不可以直接传中文，可以传加密后的带有%的字符串，比如：%E4%B8%AD%E5%9B%BD
 *
 * @author Leon
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;


    //文章搜索
    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords, @PathVariable int page, @PathVariable int size){
        Page<Article> articlePage = articleSearchService.findByKeyWord(keywords, page, size);
        return new Result(true, StatusCode.OK.getCode(),"查询成功",new PageResult<>(articlePage.getTotalElements(),articlePage.getContent()));
    }


    //添加文章
    @PostMapping
    public Result add(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK.getCode(),"操作成功");
    }
}
