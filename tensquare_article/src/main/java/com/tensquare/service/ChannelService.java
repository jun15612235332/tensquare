package com.tensquare.service;

import com.tensquare.model.Channel;
import com.tensquare.repository.ChannelRepository;
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
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private IdWorker idWorker;

    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    public Channel findById(String id) {
        return channelRepository.findById(id).get();
    }

    public void add(Channel channel) {
        channel.setId(idWorker.nextId()+"");
        channelRepository.save(channel);
    }

    public void updateById(Channel channel) {
        channelRepository.save(channel);
    }

    public void deleteById(String id) {
        channelRepository.deleteById(id);
    }

    public List<Channel> search(Channel channel) {
        List<Channel> channels = channelRepository.findAll(new Specification<Channel>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (channel.getName() != null && !"".equals(channel.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + channel.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        });
        return channels;
    }

    public Page<Channel> searchPageSize(Integer pageNo, Integer size, Channel channel) {
        Pageable pageable= PageRequest.of(pageNo-1,size);
        Page<Channel> page = channelRepository.findAll(new Specification<Channel>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<>();
                if (channel.getName() != null && !"".equals(channel.getName())) {
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + channel.getName() + "%");
                    list.add(predicate);
                }
                Predicate[] predicates = list.toArray(new Predicate[list.size()]);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }
}
