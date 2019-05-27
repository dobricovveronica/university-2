package com.amsoftgroup.service.impl;

import com.amsoftgroup.dao.*;
import com.amsoftgroup.model.*;
import com.amsoftgroup.service.StudentService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultStudentService implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private DisciplineDao disciplineDao;
    @Resource
    private GroupDao groupDao;
    @Resource
    private LibraryAbonamentDao libraryAbonamentDao;
    @Resource
    private PhonTypeDao phoneTypeDao;
    @Resource
    private MarkDao markDao;
    @Resource
    private StudentFilterDao studentFilterDao;
    @Resource
    private AddressDao addressDao;

    @Override
    public List<Student> findAllStudents() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findStudents(StudentFilter studentFilter, int start, int end){ return studentFilterDao.findStudents(studentFilter, start,  end);}

    @Override
    public List<Student> findStudentsList(StudentFilter studentFilter){ return studentFilterDao.findStudentsList(studentFilter);}

    @Override
    public int countOfStudents(StudentFilter studentFilter){ return studentFilterDao.countOfStudents(studentFilter);}

//    @Override
//    public Page<Student> findSt(StudentFilter studentFilter, Pageable pageable){ return studentFilterDao.findStudents(studentFilter, pageable);}

    @Override
    public Student findStudentByIdSetDiscipline(Long id){ return studentDao.findStudentByIdSetDiscipline(id);}

    @Override
    public Student findStudentByIdSetPhone(Long id){ return studentDao.findStudentByIdSetPhone(id);}

    @Override
    public List<Discipline> findAllDisciplines() {
        return disciplineDao.findAll();
    }

    @Override
    public List<Discipline> findDisciplinesByStudentId(Long studentId) {
        return disciplineDao.findDisciplinesByStudentId(studentId);
    }

    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAll();
    }

    @Override
    public List<LibraryAbonament> findAllLibraryAbonaments() {
        return libraryAbonamentDao.findAll();
    }

    @Override
    public LibraryAbonament addlibraryAbonament(LibraryAbonament libraryAbonament){return libraryAbonamentDao.add(libraryAbonament);}

    @Override
    public LibraryAbonament findLibraryAbonamentById(Long id){return libraryAbonamentDao.findLibraryAbonamentById(id);}

    @Override
    public List<String> findDistinctAll(){return libraryAbonamentDao.findDistinctAll();}

    @Override
    public List<PhoneType> findAllPhoneTypes(){return phoneTypeDao.findAll();}

    @Override
    public void updateLibraryAbonament(LibraryAbonament libraryAbonament){
        libraryAbonamentDao.update(libraryAbonament);
    }

    @Override
    public void addMark(Mark mark){
        markDao.add(mark);
    }

    @Override
    public void addStudent(Student student){
        studentDao.add(student);
    }

    @Override
    public void updateStudent(Student student){
        studentDao.update(student);
    }

    @Override
    public void deleteStudent(Long studentId){
        studentDao.delete(studentId);}

    @Override
    public void updateAddress(Address address){addressDao.update(address);}
}
