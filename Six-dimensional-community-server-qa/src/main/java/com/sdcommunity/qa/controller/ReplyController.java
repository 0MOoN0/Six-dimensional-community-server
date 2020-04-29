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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

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
	 * 问题回复的点赞功能，与问题点赞功能业务相同
	 * @param replyid
	 * @return
	 */
	@PutMapping("/thumbup/{replyid}/{type}")
	public Result replyThumbup(@PathVariable @NotNull String replyid, @PathVariable @NotNull String type){
		Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
		if (claims == null) {
			return new Result(false, StatusCode.ACCESSERROR.getCode(), StatusCode.ACCESSERROR.getMsg());
		}
		String uid = claims.getId();
		Boolean opsResult = redisTemplate.opsForValue().setIfAbsent("qa_" + "thumbup_" + uid + "_" + replyid, type);
		// 判断类型
		if (!opsResult) {
			String opsType = (String) redisTemplate.opsForValue().get("qa_" + "thumbup_" + uid + "_" + replyid);
			// 判断是否同类型操作
			if (!StringUtils.isEmpty(opsType) && opsType.equals(type)) {
				return new Result(true, StatusCode.REPERROR.getCode(), "你已经点过赞/踩了");
			} else {
				// 移除缓存中的key
				redisTemplate.delete("qa_" + "thumbup_" + uid + "_" + replyid);
			}
		}
		// 更新数据库
		replyService.updateThumbup(Integer.parseInt(type), replyid);
		return new Result(true, StatusCode.OK.getCode(), "操作成功");
	}
	
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
