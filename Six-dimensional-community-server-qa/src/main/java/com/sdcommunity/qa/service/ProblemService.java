package com.sdcommunity.qa.service;

import com.sdcommunity.qa.dao.ProblemDao;
import com.sdcommunity.qa.dao.ProblemLabelDao;
import com.sdcommunity.qa.pojo.Problem;
import com.sdcommunity.qa.pojo.ProblemLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ProblemService {

    @Autowired
    private ProblemLabelDao problemLabelDao;

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    public void updateThumbup(int thumbup, String problemid){
        problemDao.updateProblemThumbup(thumbup, problemid);
    }

    public List<String> findLabelByProblemId(String problemId){
        return problemDao.findLabelByProblemId(problemId);
    }

    public Page<Problem> orderSearch(String labelId, Map orderword, int currentPage, int pageSize) {
        if (orderword.get("orderword") != null && !"".equals(orderword.get("orderword"))) {
            if (orderword.get("orderword").equals("createtime")) {
                return problemDao.timeList(labelId, PageRequest.of(currentPage - 1, pageSize));
            } else if (orderword.get("orderword").equals("thumbup")) {
                return problemDao.thumbupList(labelId, PageRequest.of(currentPage - 1, pageSize));
            }
        }
        return null;
    }

    /**
     * 最新回答
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> newList(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.newList(pageable);
    }

    /**
     * 热门回答
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> hotList(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.hotList(pageable);
    }

    /**
     * 等待回答
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> waitList(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.waitList(pageable);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem) {
        String pid = String.valueOf(idWorker.nextId());
        problem.setId(pid);
        problem.setCreatetime(new Date());
        problem.setUpdatetime(new Date());
        problem.setVisits(0L);
        problem.setThumbup(0L);
        problem.setReply(0L);
        problem.setSolve("0");
        //  封装ProblemLabel
        if(problem.getLabels()!=null && problem.getLabels().size()>0){
            List<ProblemLabel> problemLabels = new ArrayList<ProblemLabel>();
            problem.getLabels().forEach(labelId->problemLabels.add(new ProblemLabel(pid, (String) labelId)));
            // 保存Problem和Label的对应关系
            problemLabelDao.saveAll(problemLabels);
        }
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap) {

        return new Specification<Problem>() {

            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 内容
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 是否解决
                if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                    predicateList.add(cb.like(root.get("solve").as(String.class), "%" + (String) searchMap.get("solve") + "%"));
                }
                // 回复人昵称
                if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                    predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + (String) searchMap.get("replyname") + "%"));
                }
                // 排序，根据关键字进行降序排序
                if (searchMap.get("orderword") != null && !"".equals(searchMap.get("orderword"))) {
                    query.orderBy(cb.desc(root.get(String.valueOf(searchMap.get("orderword"))).as(String.class)));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

}
