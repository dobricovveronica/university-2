package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.DisciplineDao;
import com.amsoftgroup.model.Discipline;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultDisciplineDao implements DisciplineDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Discipline> findAll() {
        return entityManager.createQuery("SELECT D from Discipline D").getResultList();
    }

    @Override
    public List<Discipline> findDisciplinesByStudentId(Long studentId) {
        return entityManager.createQuery("SELECT D from Discipline D where D.students.id = :id").setParameter("id", studentId).getResultList();
    }

    @Override
    public void add(Discipline discipline) {
        entityManager.persist(discipline);
    }

    @Override
    public void update(Discipline discipline) {
        entityManager.refresh(discipline);
    }
}
