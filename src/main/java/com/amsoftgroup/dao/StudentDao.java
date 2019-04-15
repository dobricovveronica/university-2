package com.amsoftgroup.dao;

import com.amsoftgroup.model.Student;

import java.util.List;

public interface StudentDao {

    List<Student> findAll();

    void add(Student student);

    void update(Student student);


}
