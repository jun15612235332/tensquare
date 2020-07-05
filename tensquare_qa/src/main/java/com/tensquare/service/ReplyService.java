package com.tensquare.service;

import com.tensquare.model.Reply;
import com.tensquare.repository.ReplyRepository;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReplyService {

    @Autowired
    private ReplyRepository repository;
    @Autowired
    private IdWorker idWorker;

    public List<Reply> findAll() {
        return repository.findAll();
    }

    public Reply findById(String id) {
        Optional<Reply> optional = repository.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void updateById(Reply reply) {
        repository.save(reply);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void insert(Reply reply) {
        reply.setId(idWorker.nextId()+"");
        repository.save(reply);
    }

    public Page<Reply> searchPageSize(Integer pageNo, Integer size, Reply reply) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Reply> page = repository.findAll(new Specification<Reply>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (reply.getContent() != null && !"".equals(reply.getContent())) {
                    predicate = criteriaBuilder.like(root.get("content").as(String.class), "%" + reply.getContent() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public List<Reply> findByProblemid(String problemId) {
        return repository.findByProblemid(problemId);
    }
}
