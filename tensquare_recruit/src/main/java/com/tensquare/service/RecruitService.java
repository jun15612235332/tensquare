package com.tensquare.service;

import com.tensquare.model.Recruit;
import com.tensquare.repository.RecruitRepository;
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
public class RecruitService{
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private IdWorker idWorker;

    public void insert(Recruit recruit) {
        recruit.setId(idWorker.nextId()+"");
        recruitRepository.save(recruit);
    }

    public List<Recruit> findAll() {
        List<Recruit> recruits = recruitRepository.findAll();
        return recruits;
    }

    public Recruit findById(String id) {
        Optional<Recruit> optional = recruitRepository.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void updateById(Recruit recruit) {
        recruitRepository.save(recruit);
    }

    public void deleteById(String id) {
        recruitRepository.deleteById(id);
    }

    public List<Recruit> search(Recruit recruit) {
        List<Recruit> recruits = recruitRepository.findAll(new Specification<Recruit>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (recruit.getJobname() != null && !"".equals(recruit.getJobname())) {
                    predicate = criteriaBuilder.like(root.get("jobname").as(String.class), "%" + recruit.getJobname() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return recruits;
    }

    public Page<Recruit> searchPageSize(Integer pageNo, Integer size, Recruit recruit) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Recruit> page = recruitRepository.findAll(new Specification<Recruit>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (recruit.getJobname() != null && !"".equals(recruit.getJobname())) {
                    predicate = criteriaBuilder.like(root.get("jobname").as(String.class), "%" + recruit.getJobname() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public List<Recruit> searchRecommend() {
        List<Recruit> recruits=recruitRepository.findTop2ByStateOrderByCreatetimeDesc("2");
        return recruits;
    }

    public List<Recruit> searchNewlist() {
        List<Recruit> recruits=recruitRepository.findTop2ByStateNotOrderByCreatetimeDesc("0");
        return recruits;
    }
}
