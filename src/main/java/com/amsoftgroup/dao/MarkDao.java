package com.amsoftgroup.dao;

import com.amsoftgroup.model.Mark;

import java.util.List;

public interface MarkDao {

    List<Mark> findAll();

    void add(Mark mark);

    void update(Mark mark);


}
