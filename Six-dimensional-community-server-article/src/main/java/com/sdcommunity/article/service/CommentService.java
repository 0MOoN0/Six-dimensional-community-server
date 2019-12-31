package com.sdcommunity.article.service;

import com.sdcommunity.article.dao.ArticleDao;
import com.sdcommunity.article.dao.CommentDao;
import com.sdcommunity.article.pojo.Article;
import com.sdcommunity.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    //根据文章ID查询评论列表
    public List<Comment> findByArticleid(String articleid){
        return commentDao.findByArticleid(articleid);
    }

    //文章评论新增
    public void add(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        comment.setPublishdate(new Date());
        // 更新article信息
        if(comment.getArticleid()!=null && !"".equals(comment.getArticleid().trim())){
            // 评论数加一
            articleDao.addComment(comment.getArticleid());
            Article article = (Article) redisTemplate.opsForValue().get("article_"+comment.getArticleid());
            Optional.ofNullable(article).ifPresent(art->art.setComment(art.getComment()+1));
            redisTemplate.opsForValue().set("article_"+comment.getArticleid(), article);
        }
        commentDao.save(comment);
    }
}
