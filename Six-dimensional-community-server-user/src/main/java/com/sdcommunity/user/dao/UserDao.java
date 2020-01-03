package com.sdcommunity.user.dao;

import com.sdcommunity.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    /**
     * 更新关注数
     * @param userid
     * @param x
     */
    @Modifying
    @Query(value = "update tb_user u set u.followcount=u.followcount+?2 where u.id=?1", nativeQuery = true)
    void incFollowcount(String userid, int x);

    /**
     * 更新粉丝数
     * @param userid
     * @param x
     */
    @Modifying
    @Query(value = "update tb_user u set u.fanscount=u.fanscount+?2 where u.id=?1",nativeQuery = true)
    void incFanscount(String userid, int x);

    //查询用户
    User findByMobile(String mobile);
}
