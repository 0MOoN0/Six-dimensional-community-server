package com.sdcommunity.friend.dao;

import com.sdcommunity.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Leon
 */
public interface FriendDao extends JpaRepository<Friend, String> {

//    Friend findByUserIdAndFriendId(String userId, String friendId);
    Friend findByuseridAndFriendid(String userId, String friendId);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike = ? WHERE userid = ? AND friendid = ?", nativeQuery = true)
    void updateIsLike(String isLike, String userId, String friendId);

    /**
     * 删除喜欢
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid=?1 AND friendid=?2", nativeQuery = true)
    void deleteFriend(String userid,String friendid);

}
