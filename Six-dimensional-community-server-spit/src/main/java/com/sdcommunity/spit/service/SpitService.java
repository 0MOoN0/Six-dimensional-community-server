package com.sdcommunity.spit.service;

import com.sdcommunity.spit.dao.SpitDao;
import com.sdcommunity.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Leon
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    protected Spit MapToPojo(Map searchMap){
        Spit spit = new Spit();
        if(searchMap == null){
            return spit;
        }
        if(searchMap.get("cid") != null){
            spit.setCid(String.valueOf(searchMap.get("cid")));
        }
        if(searchMap.get("publishtime") != null){
            spit.setPublishtime((Date) searchMap.get("cid"));
        }
        if(searchMap.get("userid") != null){
            spit.setUserid(String.valueOf(searchMap.get("userid")));
        }
        if(searchMap.get("nickname") != null){
            spit.setNickname(String.valueOf(searchMap.get("nickname")));
        }
        if(searchMap.get("visits") != null){
            spit.setVisits(Integer.parseInt(String.valueOf(searchMap.get("visits"))));
        }
        if(searchMap.get("state") != null){
            spit.setState(String.valueOf(searchMap.get("state")));
        }
        if(searchMap.get("parentid") != null){
            spit.setParentid(String.valueOf(searchMap.get("parentid")));
        }
        return spit;
    }

    public Page<Spit> findSearch(int page , int size, Map searchMap){
        Spit spit = MapToPojo(searchMap);
        Example<Spit> example = Example.of(spit);
        return spitDao.findAll(example, PageRequest.of(page - 1, size));
    }

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void save(Spit spit){
        spit.setCid(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        // 如果存在上级评论，则给上级评论的回复数加一
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query,update,Spit.class);
        }
        spitDao.save(spit);
    }

    public void update(Spit spit){
        spitDao.save(spit);
    }

    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid,pageRequest);
    }

    /**
     * 点赞操作，使用Mongo原生自增操作
     * @param id
     */
    public void updateThumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cid").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query,update,Spit.class);
    }
}
