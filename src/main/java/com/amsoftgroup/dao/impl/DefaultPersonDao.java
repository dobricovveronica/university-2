package com.amsoftgroup.dao.impl;


import com.amsoftgroup.dao.PersonDao;
import com.amsoftgroup.model.Person;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultPersonDao implements PersonDao {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public List<Person> findAll() {
    return entityManager.createQuery("SELECT P from Person P").getResultList();
  }

  @Override
  public void add(Person person) {
    entityManager.persist(person);
  }

  @Override
  public void update(Person person) {
    entityManager.merge(person);
  }
}
