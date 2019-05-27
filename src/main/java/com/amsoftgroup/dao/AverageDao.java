package com.amsoftgroup.dao;

import com.amsoftgroup.model.Average;

import java.util.List;

public interface AverageDao {

    List<Average> findAll();

    void add(Average average);

    void update(Average average);

}
