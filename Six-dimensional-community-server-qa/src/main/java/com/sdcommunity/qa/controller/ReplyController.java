package com.sdcommunity.qa.controller;

import com.sdcommunity.qa.client.UserClient;
import com.sdcommunity.qa.pojo.Reply;
import com.sdcommunity.qa.service.ReplyService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private UserClient userClient;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK.getCode(),"查询成功",replyService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true, StatusCode.OK.getCode(),"查询成功",replyService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK.getCode(),"查询成功",  new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK.getCode(),"查询成功",replyService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param reply
	 */
	@PostMapping
	public Result add(@RequestBody Reply reply){
		// TODO 20200102 Leon：发表回复时，问题的回复数+1
		Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
		if (claims == null) {
			return new Result(false, StatusCode.ACCESSERROR.getCode(), StatusCode.ACCESSERROR.getMsg());
		}
		String uid = claims.getId();
		reply.setUserid(uid);
		replyService.add(reply);
		return new Result(true, StatusCode.OK.getCode(),"增加成功");
	}
	
	/**
	 * 修改
	 * @param reply
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Reply reply, @PathVariable String id ){
		reply.setId(id);
		replyService.update(reply);		
		return new Result(true, StatusCode.OK.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		replyService.deleteById(id);
		return new Result(true, StatusCode.OK.getCode(),"删除成功");
	}
	
}
