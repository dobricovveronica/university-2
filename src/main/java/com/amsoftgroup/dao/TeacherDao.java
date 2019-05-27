package com.amsoftgroup.dao;

import com.amsoftgroup.model.Teacher;

import java.util.List;

public interface TeacherDao {

    List<Teacher> findAll();

    void add(Teacher teacher);

    void update(Teacher teacher);

}
