package com.sdcommunity.article.controller;

import com.sdcommunity.article.pojo.Article;
import com.sdcommunity.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	// 获取置顶文章
	@GetMapping("/toparticle")
	public Result getTopArticle(){
		return new Result(true, StatusCode.OK.getCode(),StatusCode.OK.getMsg(), articleService.findToparticle());
	}

	/**
	 * 文章审核
	 * @param articleId
	 * @return
	 */
	@PutMapping("/examine/{articleId}")
	public Result examine(@PathVariable String articleId) {
		articleService.updateState(articleId);
		return new Result(true,  StatusCode.OK.getCode(),  "审核成功");
	}

	/**
	 * 文章点赞
	 * @param articleId
	 * @return
	 */
	@PutMapping("/thumbup/{articleId}")
	public Result thumbup(@PathVariable String articleId) {
		// TODO 20191216 Leon 禁止重复点赞
		articleService.addThumbup(articleId);
		return new Result(true,  StatusCode.OK.getCode(),  "点赞成功");
	}

	@GetMapping("/{articleId}")
	public Result findById(@PathVariable String articleId) {
		return new Result(true,  StatusCode.OK.getCode(),  "查询成功",  articleService.findById(articleId));
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK.getCode(), "查询成功", articleService.findAll());
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}", method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Article> pageList = articleService.findSearch(searchMap,  page,  size);
		return  new Result(true, StatusCode.OK.getCode(), "查询成功",   new PageResult<Article>(pageList.getTotalElements(),  pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK.getCode(), "查询成功", articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@PostMapping
	public Result add(@RequestBody @NotNull Article article){
		// 获取用户信息
		Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
		if (claims == null) {
			return new Result(false, StatusCode.ACCESSERROR.getCode(), StatusCode.ACCESSERROR.getMsg());
		}
		String uid = claims.getId();
		article.setUserid(uid);
		articleService.add(article);
		return new Result(true, StatusCode.OK.getCode(), "增加成功", article.getId());	// 返回生成的id
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@RequestMapping(value="/{id}", method= RequestMethod.PUT)
	public Result update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);		
		return new Result(true, StatusCode.OK.getCode(), "修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		articleService.deleteById(id);
		return new Result(true, StatusCode.OK.getCode(), "删除成功");
	}
	
}
