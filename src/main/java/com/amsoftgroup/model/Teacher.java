package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "teachers", schema = "university")
public class Teacher extends Person{


    @Column(name = "salary")
    private Double salary;
}
