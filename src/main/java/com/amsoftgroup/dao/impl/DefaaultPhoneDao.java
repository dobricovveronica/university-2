package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.PhoneDao;
import com.amsoftgroup.model.Phone;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaaultPhoneDao implements PhoneDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Phone> findAll() {
        return entityManager.createQuery("SELECT Ph from Phone Ph").getResultList();
    }

    @Override
    public void add(Phone phone) {
        entityManager.persist(phone);
    }

    @Override
    public void update(Phone phone) {
        entityManager.refresh(phone);
    }
}
