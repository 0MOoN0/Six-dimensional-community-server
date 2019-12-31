package com.sdcommunity.article.dao;

import com.sdcommunity.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Leon
 */
public interface CommentDao extends MongoRepository<Comment, String> {

    /**
     * 根据文章ID查找对应的评论
     * @param articleid
     * @return
     */
    List<Comment> findByArticleid(String articleid);

}
