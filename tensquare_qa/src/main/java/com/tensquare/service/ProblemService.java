package com.tensquare.service;

import com.tensquare.model.Problem;
import com.tensquare.repository.ProblemRepository;
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
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private IdWorker idWorker;

    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    public Problem findById(String id) {
        Optional<Problem> optional = problemRepository.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void insert(Problem problem) {
        problem.setId(idWorker.nextId()+"");
        problemRepository.save(problem);
    }

    public void deleteById(String id) {
        problemRepository.deleteById(id);
    }

    public void updateById(Problem problem) {
        problemRepository.save(problem);
    }

    public List<Problem> search(Problem problem) {
        List<Problem> problems = problemRepository.findAll(new Specification<Problem>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (problem.getTitle() != null && !"".equals(problem.getTitle())) {
                    predicate = criteriaBuilder.like(root.get("title").as(String.class), "%" + problem.getTitle() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return problems;
    }

    public Page<Problem> searchPageSize(Integer pageNo, Integer size, Problem problem) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Problem> page = problemRepository.findAll(new Specification<Problem>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (problem.getTitle() != null && !"".equals(problem.getTitle())) {
                    predicate = criteriaBuilder.like(root.get("title").as(String.class), "%" + problem.getTitle() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public Page<Problem> searchNewlist(Integer pageNo, Integer size, String labelid) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return problemRepository.searchNewlist(labelid,pageable);
    }

    public Page<Problem> searchHotlist(Integer pageNo, Integer size, String labelid) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return problemRepository.searchHotlist(labelid,pageable);
    }

    public Page<Problem> searchWaitlist(Integer pageNo, Integer size, String labelid) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return problemRepository.searchWaitlist(labelid,pageable);
    }

    public Page<Problem> all(Integer pageNo, Integer size, String labelid) {
        Pageable pageable=PageRequest.of(pageNo-1,size);
        return problemRepository.all(labelid,pageable);
    }
}
