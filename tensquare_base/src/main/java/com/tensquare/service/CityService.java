package com.tensquare.service;

import com.tensquare.model.City;
import com.tensquare.repository.CityReposity;
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
public class CityService {
    @Autowired
    private CityReposity cityReposity;
    @Autowired
    private IdWorker idWorker;

    public List<City> findAll() {
        return cityReposity.findAll();
    }

    public void add(City city) {
        city.setId(idWorker.nextId()+"");
        cityReposity.save(city);
    }

    public void updateById(City city) {
        cityReposity.save(city);
    }

    public void deleteById(String id) {
        cityReposity.deleteById(id);
    }

    public City findById(String id) {
        return cityReposity.findById(id).get();
    }

    public List<City> search(City city) {
        List<City> cities = cityReposity.findAll(new Specification<City>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (city.getName() != null && !"".equals(city.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + city.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return cities;
    }

    public Page<City> searchPageSize(Integer pageNo, Integer size, City city) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<City> page = cityReposity.findAll(new Specification<City>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (city.getName() != null && !"".equals(city.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + city.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }
}
