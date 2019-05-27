package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "groups", schema = "university")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
//    private Set<Student> students;

    public Group(){}
}
