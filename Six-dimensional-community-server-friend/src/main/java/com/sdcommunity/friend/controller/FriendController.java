package com.sdcommunity.friend.controller;

import com.sdcommunity.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leon
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友或者添加不喜欢的人
     * @return
     */
    @PutMapping("/like/{friendId}/{type}")
    public Result operateFriend(@PathVariable String friendId, @PathVariable String type) {
        // 验证是否登录，并且拿到当前登录用户的id
        Claims claims_user = (Claims) httpServletRequest.getAttribute("user_claims");
        if (null == claims_user) {
            // 说明当前用户没有user角色
            return new Result(false, StatusCode.ACCESSERROR.getCode(), StatusCode.ACCESSERROR.getMsg());
        }
        // 判断是添加好友还是添加不喜欢的人
        if (type != null) {
            // 得到当前登录的用户id
            String userId = claims_user.getId();
            int flag = -1;
            String message;
            if ("1".equals(type)) {
                // 添加好友
                flag = friendService.addFriend(userId, friendId);
                message = "不能重复添加好友";
                if (flag == 1) {
                    // 更新用户的关注数跟好友的被关注数
                }
            } else if("2".equals(type)) {
                // 添加不喜欢的人
                flag = friendService.addNoFriend(userId, friendId);
                message = "不能重复添加非好友";
            } else {
                return new Result(false, StatusCode.ERROR.getCode(), StatusCode.ERROR.getMsg());
            }
            // 对flag进行判断
            if (flag == 0) {
                return new Result(false, StatusCode.ERROR.getCode(), message);
            } else if (flag == 1) {
                return new Result(true, StatusCode.OK.getCode(), StatusCode.OK.getMsg());
            }
        }
        // 类型参数为空
        return new Result(false,StatusCode.PARAMERROR.getCode(),StatusCode.PARAMERROR.getMsg());
    }


}
