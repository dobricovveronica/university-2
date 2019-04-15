package com.amsoftgroup.dao;

import com.amsoftgroup.model.LibraryAbonament;

import java.util.List;

public interface LibraryAbonamentDao {

    List<LibraryAbonament> findAll();

    void add(LibraryAbonament libraryAbonament);

    void update(LibraryAbonament libraryAbonament);


}
