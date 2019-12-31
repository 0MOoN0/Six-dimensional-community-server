package com.sdcommunity.article.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leon
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleDaoTest {

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testUpdate(){
    }

}
