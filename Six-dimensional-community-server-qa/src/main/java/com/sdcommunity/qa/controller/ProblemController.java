package com.sdcommunity.qa.controller;

import com.sdcommunity.qa.client.LabelClient;
import com.sdcommunity.qa.pojo.Problem;
import com.sdcommunity.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Leon
 */
@RequestMapping("/problem")
@RestController
@CrossOrigin
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LabelClient labelClient;

    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable("labelId") String labelId){
        Result result = labelClient.findById(labelId);
        return result;
    }

    @PostMapping("/ordersearch/{label}/{page}/{size}")
    public Result orderSearch(@PathVariable("label")String labelId, @RequestBody Map keyword,
                              @PathVariable("page")int currentPage,
                              @PathVariable("size")int pageSize){
        Page<Problem> pageData = null;
        if(keyword.get("orderword")!=null){
            pageData = problemService.orderSearch(labelId, keyword, currentPage, pageSize);
        }
        // 可能会抛出空指针异常
        return new Result(true, StatusCode.OK.getCode(), StatusCode.OK.getMsg(),new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 添加问题，需要用户登陆
     * @param problem
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Problem problem){
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR.getCode(), StatusCode.ACCESSERROR.getMsg());
        }
        problemService.add(problem);
        return new Result(true,StatusCode.OK.getCode(),StatusCode.OK.getMsg());
    }

    /**
     * 最新问答
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/newlist/{page}/{size}")
    public Result newList(@PathVariable("page") int currentPage, @PathVariable("size") int pageSize){
        Page<Problem> pageData = problemService.newList(currentPage, pageSize);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }
    /**
     * 热门问答
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/hotlist/{page}/{size}")
    public Result hotList(@PathVariable("page") int currentPage,
                          @PathVariable("size") int pageSize) {
        Page<Problem> pageData = problemService.hotList(currentPage, pageSize);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 等待问答
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/waitlist/{page}/{size}")
    public Result waitList(@PathVariable("page") int currentPage,
                           @PathVariable("size") int pageSize) {
        Page<Problem> pageData = problemService.waitList(currentPage, pageSize);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }


    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK.getCode(),"查询成功",problemService.findAll());
    }

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK.getCode(),"查询成功",problemService.findById(id));
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
        Page<Problem> pageList = null;
        pageList = problemService.findSearch(searchMap, page, size);
        return  new Result(true,StatusCode.OK.getCode(),"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK.getCode(),"查询成功",problemService.findSearch(searchMap));
    }


    /**
     * 修改
     * @param problem
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id ){
        problem.setId(id);
        problemService.update(problem);
        return new Result(true,StatusCode.OK.getCode(),"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        problemService.deleteById(id);
        return new Result(true,StatusCode.OK.getCode(),"删除成功");
    }

}