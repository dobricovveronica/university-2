package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.MarkDao;
import com.amsoftgroup.model.Mark;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultMarkDao implements MarkDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Mark> findAll() {
        return entityManager.createQuery("SELECT M from Mark m").getResultList();
    }

    @Override
    public void add(Mark mark) {
        entityManager.persist(mark);
    }

    @Override
    public void update(Mark mark) {
        entityManager.refresh(mark);
    }
}
