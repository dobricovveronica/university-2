package com.amsoftgroup.service.impl;

import com.amsoftgroup.dao.PersonDao;
import com.amsoftgroup.model.Person;
import com.amsoftgroup.service.PersonService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class DefaultPersonService implements PersonService {

  @Resource
  private PersonDao personDao;

  @Override
  public List<Person> findAll() {
    return personDao.findAll();
  }

}
