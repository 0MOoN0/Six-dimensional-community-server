package com.sdcommunity.article.dao;

import com.sdcommunity.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leon
 */
public interface ArticleDao extends JpaRepository<Article,String>, JpaSpecificationExecutor<Article> {

    @Modifying
    @Query(value="UPDATE tb_article SET state=1 WHERE id = ?", nativeQuery = true)
    void updateState(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup = thumbup + 1 WHERE id = ?", nativeQuery = true)
    void addThumbup(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET comment = comment + 1 WHERE id = ?",nativeQuery = true)
    void addComment(String id);

    List<Article> findAllByIstopEqualsAndIspublic(String istop, String ispublic);


}
