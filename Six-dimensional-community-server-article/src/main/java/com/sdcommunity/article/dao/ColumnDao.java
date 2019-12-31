package com.sdcommunity.article.dao;

import com.sdcommunity.article.pojo.Article;
import com.sdcommunity.article.pojo.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leon
 */
public interface ColumnDao extends JpaRepository<Column,String>, JpaSpecificationExecutor<Column> {
}
