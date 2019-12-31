package com.sdcommunity.search.service;

import com.sdcommunity.search.dao.ArticleSearchDao;
import com.sdcommunity.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    //增加文章
    public void add(Article article){
        articleSearchDao.save(article);
    }

    public Page<Article> findByKeyWord(String keyWord, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return articleSearchDao.findByTitleOrContentLike(keyWord, keyWord, pageable);
    }
}
