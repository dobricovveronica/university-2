package com.amsoftgroup.dao;

import com.amsoftgroup.model.Discipline;

import java.util.List;

public interface DisciplineDao{

    List<Discipline> findAll();

    List<Discipline> findDisciplinesByStudentId(Long studentId);

    void add(Discipline discipline);

    void update(Discipline discipline);

}
