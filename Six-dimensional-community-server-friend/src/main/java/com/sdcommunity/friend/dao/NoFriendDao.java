package com.sdcommunity.friend.dao;


import com.sdcommunity.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    NoFriend findByUseridAndFriendid(String userid, String friendid);
}
