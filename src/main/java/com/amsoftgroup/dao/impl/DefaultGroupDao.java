package com.amsoftgroup.dao.impl;


import com.amsoftgroup.dao.GroupDao;
import com.amsoftgroup.model.Group;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultGroupDao implements GroupDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Group> findAll() {
        return entityManager.createQuery("SELECT G from Group G").getResultList();
    }

    @Override
    public void add(Group group) {
        entityManager.persist(group);
    }

    @Override
    public void update(Group group) {
        entityManager.refresh(group);
    }
}
