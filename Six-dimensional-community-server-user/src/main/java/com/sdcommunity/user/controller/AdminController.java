package com.sdcommunity.user.controller;
import com.sdcommunity.user.pojo.Admin;
import com.sdcommunity.user.service.AdminService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/info")
	public Result getAdminInfo(@RequestParam("token") String token){
		// 可能会抛解析错误的异常
		Claims claims = jwtUtil.parseJWT(token);
		String role = (String) claims.get("roles");
		String adminName = claims.getSubject();
		String [] roles = new String[]{role};
		Map<String, Object> data = new HashMap<String, Object>(4,1);
		data.put("roles",roles);
		data.put("name",adminName);
		data.put("role", role);
		return new Result(true, StatusCode.OK.getCode(), StatusCode.OK.getMsg(), data);
	}

	@PostMapping("/login")
	public Result login(@RequestBody Admin admin){
		admin = adminService.login(admin);
		if(admin == null){
			return new Result(false, StatusCode.LOGINERROR.getCode(),StatusCode.LOGINERROR.getMsg());
		}
		String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
		Map<String, Object> map = new HashMap<String, Object>(2,1);
		map.put("token", token);
		map.put("role","admin");
		return new Result(true, StatusCode.OK.getCode(), StatusCode.OK.getMsg(),map);
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK.getCode(),"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true, StatusCode.OK.getCode(),"查询成功",adminService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method= RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK.getCode(),"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK.getCode(),"查询成功",adminService.findSearch(searchMap));
    }
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true, StatusCode.OK.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true, StatusCode.OK.getCode(),"删除成功");
	}
	
}
