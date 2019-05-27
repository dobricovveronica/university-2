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
    public LibraryAbonament add(LibraryAbonament libraryAbonament) {
        entityManager.persist(libraryAbonament);
        return libraryAbonament;
    }

    @Override
    public void update(LibraryAbonament libraryAbonament) {
        entityManager.merge(libraryAbonament);
    }

    @Override
    public LibraryAbonament findLibraryAbonamentById(Long id){
        LibraryAbonament libraryAbonament = (LibraryAbonament) entityManager.find(LibraryAbonament.class, id);
        return libraryAbonament;
    }

    @Override
    public List<String> findDistinctAll() {
        return entityManager.createQuery("SELECT DISTINCT L.status from LibraryAbonament L ").getResultList();
    }
}
