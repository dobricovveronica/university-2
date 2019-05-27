package com.amsoftgroup.service;

import com.amsoftgroup.model.*;



import java.util.List;
import java.util.Set;

public interface StudentService {

    List<Student> findAllStudents();

    List<Student> findStudents(StudentFilter studentFilter, int start, int end);

    List<Student> findStudentsList(StudentFilter studentFilter);

    int countOfStudents(StudentFilter studentFilter);

//    Page<Student> findSt(StudentFilter studentFilter,  Pageable pageable);

    Student findStudentByIdSetDiscipline(Long id);

    Student findStudentByIdSetPhone(Long id);

    List<Discipline> findAllDisciplines();

    List<Discipline> findDisciplinesByStudentId(Long studentId);

    List<Group> findAllGroups();

    List<LibraryAbonament> findAllLibraryAbonaments();

    LibraryAbonament findLibraryAbonamentById(Long id);

    List<String> findDistinctAll();

    LibraryAbonament addlibraryAbonament(LibraryAbonament libraryAbonament);

    List<PhoneType> findAllPhoneTypes();

    void updateLibraryAbonament(LibraryAbonament libraryAbonament);

    void addMark(Mark mark);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Long studentId);

    void updateAddress(Address address);

//    void addAddress(Address address);

}
