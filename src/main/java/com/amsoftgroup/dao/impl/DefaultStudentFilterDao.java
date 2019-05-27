package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.StudentFilterDao;
import com.amsoftgroup.model.Average;
import com.amsoftgroup.model.Student;
import com.amsoftgroup.model.StudentFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

@Transactional
@Repository
public class DefaultStudentFilterDao implements StudentFilterDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> findStudents(StudentFilter studentFilter, int start, int end){

        Query query = createCriteriaBuilder(studentFilter);
        query.setFirstResult(start);
        query.setMaxResults(end);
        List<Student> students = query.getResultList();
        if (studentFilter.getAverage() != null) {

            return averageFilter(students, studentFilter.getAverage());
        }

        return students;
    }

    @Override
    public List<Student> findStudentsList(StudentFilter studentFilter) {


        Query query =createCriteriaBuilder(studentFilter);
        List<Student> students = query.getResultList();
        if (studentFilter.getAverage() != null) {
            return averageFilter(students, studentFilter.getAverage());
        }

        return students;
    }

    @Override
    public int countOfStudents(StudentFilter studentFilter) {

        return findStudentsList(studentFilter).size();
    }

    public Query createCriteriaBuilder(StudentFilter studentFilter){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class).distinct(true);
        Root<Student> student = criteriaQuery.from(Student.class);
        Join disciplineJoin = student.join("disciplines", JoinType.LEFT);
        Fetch disciplineFetch = student.fetch("disciplines", JoinType.LEFT);
        Join phoneJoin = student.join("phones", JoinType.LEFT);
        Fetch phoneFetch = student.fetch("phones", JoinType.LEFT);
        Predicate predicateName = null;
        Predicate predicateAddress = null;
        Predicate predicateGroupId = null;
        Predicate predicateDisciplineId = null;
        Predicate predicateDisciplineTitle = null;
        Predicate predicateDate = null;
        Predicate predicateDateBetween = null;
        Predicate predicateGender = null;
        List<Predicate> criteriaList = new ArrayList<>();
        if (studentFilter.getStudentName() != null) {
            if (!studentFilter.getStudentName().equals("")) {
                predicateName = criteriaBuilder.or(criteriaBuilder.like(student.get("firstName"), studentFilter.getStudentName() + "%"),
                        criteriaBuilder.like(student.get("lastName"), studentFilter.getStudentName() + "%"));
                criteriaList.add(predicateName);
            }
            if (!studentFilter.getStudentAddress().equals("")) {
                predicateAddress = criteriaBuilder.or(criteriaBuilder.like(student.get("address").get("country"), studentFilter.getStudentAddress() + "%"),
                        criteriaBuilder.like(student.get("address").get("city"), studentFilter.getStudentAddress() + "%"),
                        criteriaBuilder.like(student.get("address").get("address"), studentFilter.getStudentAddress() + "%"));
                criteriaList.add(predicateAddress);
            }
            if (studentFilter.getGroupId() != null) {
                predicateGroupId = criteriaBuilder.equal(student.get("group").get("id"), studentFilter.getGroupId());
                criteriaList.add(predicateGroupId);
            }
            if (studentFilter.getDisciplineId() != null) {
                predicateDisciplineId = criteriaBuilder.equal(disciplineJoin.get("id"), studentFilter.getDisciplineId());
                criteriaList.add(predicateDisciplineId);
            }
            if (!studentFilter.getDisciplineTitle().equals("")) {
                predicateDisciplineTitle = criteriaBuilder.like(disciplineJoin.get("title"), studentFilter.getDisciplineTitle() + "%");
                criteriaList.add(predicateDisciplineTitle);
            }
            if (studentFilter.getStartDate() != null && studentFilter.getEndDate() == null) {
                predicateDate = criteriaBuilder.equal(student.get("dateOfBirth"), studentFilter.getStartDate());
                criteriaList.add(predicateDate);
            }
            if (studentFilter.getStartDate() != null && studentFilter.getEndDate() != null) {
                predicateDateBetween = criteriaBuilder.between(student.get("dateOfBirth"), studentFilter.getStartDate(), studentFilter.getEndDate());
                criteriaList.add(predicateDateBetween);
            }
            if (studentFilter.getGender() != 'T') {
                predicateGender = criteriaBuilder.equal(student.get("gender"), studentFilter.getGender());
                criteriaList.add(predicateGender);
            }
        }

        if (studentFilter.getStudentName() != null) {
            if (!studentFilter.getStudentName().equals("")
                    || !studentFilter.getStudentAddress().equals("")
                    || !studentFilter.getDisciplineTitle().equals("")
                    || studentFilter.getDisciplineId() != null || studentFilter.getStartDate() != null || studentFilter.getEndDate() != null
                    || studentFilter.getGroupId() != null || studentFilter.getGender() != 'T') {
                criteriaQuery.where(
                        criteriaBuilder.and(criteriaList.toArray(new Predicate[0]))
                );
            }
        }
        Query query = entityManager.createQuery(criteriaQuery);
        return query;
    }

    public List<Student> averageFilter(List<Student> studentList, Double average){
       List<Student> listOfStudents = new ArrayList<>();
        for (Student st : studentList) {
            if (st.studentAverage().equals(average)){
                listOfStudents.add(st);
            }
        }
        return listOfStudents;
    }


}
