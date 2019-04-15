package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.StudentDao;
import com.amsoftgroup.model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultStudentDao implements StudentDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("SELECT S from Student S ").getResultList();
    }

    @Override
    public void add(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void update(Student student) {
        entityManager.refresh(student);
    }
}
