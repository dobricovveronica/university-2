package com.amsoftgroup.dao;

import com.amsoftgroup.model.LibraryAbonament;

import java.util.List;

public interface LibraryAbonamentDao {

    List<LibraryAbonament> findAll();

    LibraryAbonament add(LibraryAbonament libraryAbonament);

    void update(LibraryAbonament libraryAbonament);

    LibraryAbonament findLibraryAbonamentById(Long id);

    List<String> findDistinctAll();
}
