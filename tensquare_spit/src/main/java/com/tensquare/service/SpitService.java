package com.tensquare.service;

import com.tensquare.model.Spit;
import com.tensquare.repository.SpitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitRepository spitRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitRepository.findAll();
    }

    public void insert(Spit spit) {
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("0");
        spit.setParentid("0");
        spitRepository.save(spit);
    }

    public Spit findById(String id) {
        return spitRepository.findById(id).get();
    }

    public void updateById(Spit spit) {
        spitRepository.save(spit);
    }

    public void delete(String id) {
        spitRepository.deleteById(id);
    }

    public List<Spit> search(Spit spit) {
        List<Spit> spits = spitRepository.findBy_idLikeAndContentLike(spit.get_id(),spit.getContent());
        return spits;
    }

    public void thumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public Page<Spit> comment(String parentid, Integer pageNo, Integer size) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        return spitRepository.findByParentid(parentid,pageable);
    }
}
