package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "disciplines", schema = "university")
public class Discipline {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "university.disciplines_to_students", joinColumns = {@JoinColumn(name = "discipline_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    private Set<Student> students = new HashSet<>();

}
