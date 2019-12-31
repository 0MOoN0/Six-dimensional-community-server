package com.sdcommunity.article.dao;

import com.sdcommunity.article.pojo.Article;
import com.sdcommunity.article.pojo.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leon
 */
public interface ChannelDao extends JpaRepository<Channel,String>, JpaSpecificationExecutor<Channel> {
}
