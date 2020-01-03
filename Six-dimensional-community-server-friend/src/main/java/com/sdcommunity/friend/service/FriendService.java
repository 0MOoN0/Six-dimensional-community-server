package com.sdcommunity.friend.service;

import com.netflix.discovery.converters.Auto;
import com.sdcommunity.friend.client.UserClient;
import com.sdcommunity.friend.dao.FriendDao;
import com.sdcommunity.friend.dao.NoFriendDao;
import com.sdcommunity.friend.pojo.Friend;
import com.sdcommunity.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Leon
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友；
     * 判断是否已经有数据，有数据则表明重复添加了；
     * 如果没有数据，判断对方是否已经添加，如果对方已经添加，则说明这是个双向关系，如果没有，则是单向关系
     * @param userId
     * @param friendId
     * @return
     */
    public int addFriend(String userId, String friendId) {
        // TODO 20200103 Leon 校验处理？，friendId对应用户有可能不存在
        // TODO 20200103 Leon 能否添加自己为好友？
        // 先判断userId到friendId是否有数据，有就是重复添加好友了，返回0
        Friend friend = friendDao.findByuseridAndFriendid(userId, friendId);
        if (friend != null) {
            return 0;
        }
        // 直接添加好友， 让好友表中userId到friendId方向的type为0
        friend = new Friend();
        friend.setIslike("0");
        friend.setFriendid(friendId);
        friend.setUserid(userId);
        // 判断从friendId到userId是否有数据，如果有，把双方的状态都改为1
        Friend friendLikeMe = friendDao.findByuseridAndFriendid(friendId, userId);
        if (friendLikeMe != null) {
            // 把双方的isLike都改为1
            friendDao.updateIsLike("1", friendId, userId);
            friend.setIslike("1");
        }
        friendDao.save(friend);

        // friendId增加粉丝数
        userClient.incFanscount(friendId,1);
        // UserId增加关注数
        userClient.incFollowcount(userId,1);
        return 1;
    }

    /**
     * 添加非好友
     * @param userId
     * @param friendId
     * @return
     */
    public int addNoFriend(String userId, String friendId) {
        // TODO 20200103 Leon：能否添加自己为非好友、添加非好友的ID也在好友列表中如何处理？
        // 先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setFriendid(friendId);
        noFriend.setUserid(userId);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        Friend friend = friendDao.findByuseridAndFriendid(userid, friendid);
        if(friend==null){
            throw new RuntimeException("你还未关注他/她");
        }
        // 删除好友关系
        friendDao.deleteFriend(userid,friendid);
        // 更新关系为单向关系，如果对方没有关注自己，则这条语句找不到对应的数据进行修改，符合业务要求
        friendDao.updateIsLike("0",userid,friendid);
        // TODO 20200103 Leon：是否需要将不关注的对象添加到非好友列表？
        // 添加到非好友列表
//        addNoFriend(userid,friendid);
        // 减少关注数和对方的粉丝数
        userClient.incFollowcount(userid,-1);
        userClient.incFanscount(friendid,-1);

    }
}
