package com.amsoftgroup.dao.impl;


import com.amsoftgroup.dao.AddressDao;
import com.amsoftgroup.model.Address;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultAddressDao implements AddressDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Address> findAll() {
        return entityManager.createQuery("SELECT Ad from Address Ad").getResultList();
    }

    @Override
    public void add(Address address) {
        entityManager.persist(address);
    }

    @Override
    public void update(Address address) {
        entityManager.refresh(address);
    }
}
