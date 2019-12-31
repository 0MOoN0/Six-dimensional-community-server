package com.sdcommunity.search.dao;

import com.sdcommunity.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Leon
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {
    /**
     * 根据标题或文章查找
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
