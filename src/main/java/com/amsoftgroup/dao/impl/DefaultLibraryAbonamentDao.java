package com.amsoftgroup.dao.impl;


import com.amsoftgroup.dao.LibraryAbonamentDao;

import com.amsoftgroup.model.LibraryAbonament;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class DefaultLibraryAbonamentDao implements LibraryAbonamentDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<LibraryAbonament> findAll() {
        return entityManager.createQuery("SELECT L from LibraryAbonament L").getResultList();
    }

    @Override
    public void add(LibraryAbonament libraryAbonament) {
        entityManager.persist(libraryAbonament);
    }

    @Override
    public void update(LibraryAbonament libraryAbonament) {
        entityManager.refresh(libraryAbonament);
    }
}
