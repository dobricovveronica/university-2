//package com.amsoftgroup.controller;
//
//
//import com.amsoftgroup.model.Person;
//import com.amsoftgroup.service.PersonService;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//@RestController
//public class PersonController {
//
//  @Resource
//  private PersonService personService;
//
//  @GetMapping(value = "/")
//  public ModelAndView findAll(){
//    List<Person> persons = personService.findAll();
//
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.addObject("persons", persons);
//    modelAndView.setViewName("list");
//    modelAndView.setStatus(HttpStatus.OK);
//   return modelAndView;
//  }
//}
