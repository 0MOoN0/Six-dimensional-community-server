package com.sdcommunity.recruit.dao;


import com.sdcommunity.recruit.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
//    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id = problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM tb_label WHERE recommend=1 ORDER BY fans DESC", nativeQuery = true)
    List<Label> hotlist();

}
