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
        return entityManager.createQuery("SELECT distinct S from Student S left join fetch S.disciplines left join fetch S.phones").getResultList();
    }

    @Override
    public void add(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    public Student findStudentByIdSetDiscipline(Long id) {
//        Student student = entityManager.find(Student.class, id);
        Student student = (Student) entityManager.createQuery("SELECT S from Student S left join fetch S.disciplines where S.id = :id").setParameter("id", id).getSingleResult();
        return  student;
    }

    @Override
    public Student findStudentByIdSetPhone(Long id) {
        Student student = (Student) entityManager.createQuery("SELECT S from Student S left join fetch S.phones where S.id = :id").setParameter("id", id).getSingleResult();
        return  student;
    }

    @Override
    public void delete(Long studentId){
        Student student = entityManager.find(Student.class,studentId);
        entityManager.remove(student);
    }
}
