package com.sdcommunity.qa.dao;

import com.sdcommunity.qa.pojo.Problem;
import com.sdcommunity.qa.pojo.ProblemLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leon
 */
public interface ProblemLabelDao extends JpaRepository<ProblemLabel, ProblemLabel>, JpaSpecificationExecutor<ProblemLabel> {
}
