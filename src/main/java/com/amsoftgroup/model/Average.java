package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "averages", schema = "university")
public class Average {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "university.students", joinColumns = {@JoinColumn(name = "id", referencedColumnName = "student_id")})
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @Column(name = "value")
    private Double value;
}
