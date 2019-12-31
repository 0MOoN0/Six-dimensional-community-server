package com.sdcommunity.recruit.service;

import com.sdcommunity.recruit.dao.LabelDao;
import com.sdcommunity.recruit.pojo.Label;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Leon
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Service {

    @Autowired
    private LabelDao labelDao;

    public void testFindAll(Label label){
        List<Label> list =  labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new LinkedList<>();
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelname);
                }
                if (!StringUtils.isEmpty(label.getState())) {
                    Predicate state = cb.like(root.get("state").as(String.class), label.getState());
                    list.add(state);
                }
                Predicate[] array = new Predicate[list.size()];
                array = list.toArray(array);
                return cb.and(array);
            }
        });
    }


}
