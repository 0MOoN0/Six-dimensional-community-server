package com.sdcommunity.qa.dao;

import com.sdcommunity.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "SELECT * FROM tb_problem ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> newList(Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> hotList(Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem WHERE reply = 0 ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> waitList(Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> timeList(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? ORDER BY thumbup DESC", nativeQuery = true)
    Page<Problem> thumbupList(String labelId, Pageable pageable);

/*    @Query(value = "SELECT pl.labelid as labelid, p.id as id, p.title as title, p.content as content, p.createtime as createtime, p.updatetime as updatetime, p.userid as userid, p.visits as visits, p.thumbup as thumbup, p.reply as reply, p.solve as solve, p.replyname as replyname, p.replytime as replytime " +
            "FROM tb_pl pl ,tb_problem p " +
            "WHERE pl.problemid = p.id " +
            "AND p.id = ?1", nativeQuery = true)
    List<Map<String,Object>> findByLabelId(String problemId);*/

}
