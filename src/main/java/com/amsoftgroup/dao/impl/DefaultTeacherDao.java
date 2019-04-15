package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.TeacherDao;
import com.amsoftgroup.model.Teacher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultTeacherDao implements TeacherDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Teacher> findAll() {
        return entityManager.createQuery("SELECT T from Teacher T").getResultList();
    }

    @Override
    public void add(Teacher teacher) {
        entityManager.persist(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        entityManager.refresh(teacher);
    }
}
