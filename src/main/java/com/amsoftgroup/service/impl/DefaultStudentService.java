package com.amsoftgroup.service.impl;

import com.amsoftgroup.dao.StudentDao;
import com.amsoftgroup.model.Student;
import com.amsoftgroup.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class DefaultStudentService implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }
}
