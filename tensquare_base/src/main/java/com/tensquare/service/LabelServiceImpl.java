package com.tensquare.service;

import com.tensquare.model.Label;
import com.tensquare.repository.LabelRepository;
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

@Service
@Transactional
public class LabelServiceImpl implements LabelService{
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private IdWorker idWorker;
    @Override
    public List<Label> findAll() {
        List<Label> labels = labelRepository.findAll();
        return labels;
    }

    @Override
    public void insert(Label label) {
        label.setId(idWorker.nextId()+"");
        labelRepository.save(label);
    }

    @Override
    public Label findLabelById(String id) {
        Label label = labelRepository.findById(id).get();
        return label;
    }

    @Override
    public void updateById(Label label) {
        labelRepository.save(label);
    }

    @Override
    public void delete(String id) {
        labelRepository.deleteById(id);
    }

    @Override
    public List<Label> findAllBySearch(Label label) {
        List<Label> labels = labelRepository.findAll(new Specification<Label>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (!"".equals(label.getId()) && label.getId()!=null) {
                    predicate = criteriaBuilder.equal(root.get("id").as(String.class),label.getId());
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return labels;
    }

    @Override
    public Page<Label> searchAddPage(Integer pageNo, Integer size, Label label) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Label> labels = labelRepository.findAll(new Specification<Label>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (!"".equals(label.getId()) && label.getId() != null) {
                    predicate = criteriaBuilder.equal(root.get("id").as(String.class), label.getId());
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return labels;
    }
}
