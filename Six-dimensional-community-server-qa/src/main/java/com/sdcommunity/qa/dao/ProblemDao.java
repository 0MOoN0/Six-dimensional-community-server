package com.sdcommunity.qa.dao;

import com.sdcommunity.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
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

    @Query(value = "SELECT pl.labelid FROM tb_problem p, tb_pl pl " +
            "WHERE " +
            "p.id = pl.problemid " +
            "AND " +
            "p.id = ?1",
    nativeQuery = true)
    List<String> findLabelByProblemId(String problemId);

    @Query(value="UPDATE tb_problem SET reply = reply + ?1 WHERE id = ?2",nativeQuery = true)
    @Modifying
    int updateReplyCount(int count, String problemId);

    @Query(value="UPDATE tb_problem SET reply = reply + 1, replyname = ?1, replytime = ?2 WHERE id = ?3", nativeQuery = true)
    @Modifying
    int updateProblemReply(String replyName, Date replytime, String ProblemId);

    @Query(value = "UPDATE tb_problem SET thumbup = thumbup + ?1 WHERE id = ?2", nativeQuery = true)
    @Modifying
    int updateProblemThumbup(int thumbup, String problemId);

}
