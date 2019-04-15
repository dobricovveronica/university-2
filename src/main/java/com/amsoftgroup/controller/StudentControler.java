package com.amsoftgroup.controller;

import com.amsoftgroup.dao.DisciplineDao;
import com.amsoftgroup.model.Discipline;
import com.amsoftgroup.model.Student;
import com.amsoftgroup.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.annotation.Resource;

@RestController
public class StudentControler {

    @Resource
    private StudentService studentService;
   private DisciplineDao disciplineDao;

    @GetMapping(value = "/")
    public ModelAndView findAll(){
        List<Student> students = studentService.findAll();
//        List<Discipline> disciplines = disciplineDao.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
//        modelAndView.addObject("disciplines", disciplines);
        modelAndView.setViewName("list");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }
}
