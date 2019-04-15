package com.amsoftgroup.dao.impl;

import com.amsoftgroup.dao.PhonTypeDao;
import com.amsoftgroup.model.PhoneType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultPhoneTypeDao implements PhonTypeDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PhoneType> findAll() {
        return entityManager.createQuery("SELECT Pt from PhoneType Pt").getResultList();
    }

    @Override
    public void add(PhoneType phoneType) {
        entityManager.persist(phoneType);
    }

    @Override
    public void update(PhoneType phoneType) {
        entityManager.refresh(phoneType);
    }
}
