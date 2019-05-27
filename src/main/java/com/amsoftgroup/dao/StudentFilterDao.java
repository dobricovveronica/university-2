package com.amsoftgroup.dao;

import com.amsoftgroup.model.Student;
import com.amsoftgroup.model.StudentFilter;

import java.util.List;
import java.util.Set;

public interface StudentFilterDao {

    List<Student> findStudents(StudentFilter studentFilter, int start, int end);

    List<Student> findStudentsList(StudentFilter studentFilter);

    int countOfStudents(StudentFilter studentFilter);


//    Page<Student> findStudents(StudentFilter studentFilter, Pageable pageable);


}
