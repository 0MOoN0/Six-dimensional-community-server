package com.dscommunity.qa.dao;

import com.dscommunity.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> newList(String labelId, Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> hotList(String labelId, Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? AND reply = 0 ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> waitList(String labelId, Pageable pageable);

}
