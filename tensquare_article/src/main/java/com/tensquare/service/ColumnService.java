package com.tensquare.service;

import com.tensquare.model.Column;
import com.tensquare.repository.ColumnRepository;
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
public class ColumnService {
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private IdWorker idWorker;

    public List<Column> findAll() {
        return columnRepository.findAll();
    }

    public Column findById(String id) {
        return columnRepository.findById(id).get();
    }

    public void add(Column column) {
        column.setId(idWorker.nextId()+"");
        columnRepository.save(column);
    }

    public void updateById(Column column) {
        columnRepository.save(column);
    }

    public void deleteById(String id) {
        columnRepository.deleteById(id);
    }

    public List<Column> search(Column column) {
        List<Column> columns = columnRepository.findAll(new Specification<Column>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (column.getName() != null && !"".equals(column.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + column.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return columns;
    }

    public Page<Column> searchPageSize(Integer pageNo, Integer size, Column column) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Column> page = columnRepository.findAll(new Specification<Column>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (column.getName() != null && !"".equals(column.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + column.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    public List<Column> findByUserid(String userId) {
        return columnRepository.findByUserid(userId);
    }

    public void apply(Column column) {
        column.setId(idWorker.nextId()+"");
        columnRepository.save(column);
    }

    public void examine(String columnId) {
        columnRepository.examine(columnId);
    }
}
