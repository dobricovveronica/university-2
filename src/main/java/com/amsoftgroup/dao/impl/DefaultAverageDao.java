package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.AverageDao;
import com.amsoftgroup.model.Average;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultAverageDao implements AverageDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Average> findAll() {
        return entityManager.createQuery("SELECT A from Average A").getResultList();
    }

    @Override
    public void add(Average average) {
        entityManager.persist(average);
    }

    @Override
    public void update(Average average) {
        entityManager.refresh(average);
    }
}
