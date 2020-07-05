package com.tensquare.service;

import com.tensquare.model.Label;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LabelService {
    List<Label> findAll();

    void insert(Label label);

    Label findLabelById(String id);

    void updateById(Label label);

    void delete(String id);

    List<Label> findAllBySearch(Label label);

    Page<Label> searchAddPage(Integer pageNo, Integer size, Label label);
}
