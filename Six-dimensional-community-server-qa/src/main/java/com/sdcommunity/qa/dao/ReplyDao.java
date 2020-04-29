package com.sdcommunity.qa.dao;

import com.sdcommunity.qa.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{

    @Query(value = "UPDATE tb_reply SET thumbup = thumbup + ?1 WHERE id = ?2", nativeQuery = true)
    @Modifying
    int updateReplyThumbup(int thumbup, String replyId);
}
