package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "students", schema = "university")
public class Student extends Person{


    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "university.disciplines_to_students", joinColumns = {
            @JoinColumn(name = "student_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "discipline_id", referencedColumnName = "id")})
    private Set<Discipline> disciplines = new HashSet<>();
    public Student(){}
}
