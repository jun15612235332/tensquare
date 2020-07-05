package com.tensquare.service;

import com.tensquare.model.Enterprise;
import com.tensquare.repository.EnterpriseRepository;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private IdWorker idWorker;

    public List<Enterprise> findAll() {
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        return enterprises;
    }

    public Enterprise findById(String id) {
        Enterprise enterprise = enterpriseRepository.findById(id).get();
        return enterprise;
    }

    public void insert(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId()+"");
        enterpriseRepository.save(enterprise);
    }

    public void updateById(Enterprise enterprise) {
        enterpriseRepository.save(enterprise);
    }

    public void deleteById(String id) {
        enterpriseRepository.deleteById(id);
    }

    public List<Enterprise> search(Enterprise enterprise) {
        List<Enterprise> enterprises = enterpriseRepository.findAll(new Specification<Enterprise>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (enterprise.getName() != null && !"".equals(enterprise.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + enterprise.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return enterprises;
    }

    public Page<Enterprise> searchPageSize(Integer pageNo, Integer size, Enterprise enterprise) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Enterprise> page = enterpriseRepository.findAll(new Specification<Enterprise>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (enterprise.getName() != null && !"".equals(enterprise.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + enterprise.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public List<Enterprise> searchHotlist(String s) {
        return enterpriseRepository.findByIshot(s);
    }
}
