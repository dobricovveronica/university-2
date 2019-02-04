package com.amsoftgroup.dao;


import com.amsoftgroup.model.Person;

import java.util.List;

public interface PersonDao {

  List<Person> findAll();

  void add(Person person);

  void update(Person person);
}
