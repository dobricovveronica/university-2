package com.amsoftgroup.dao;

import com.amsoftgroup.model.Group;

import java.util.List;

public interface GroupDao {

    List<Group> findAll();

    void add(Group group);

    void update(Group group);

}
