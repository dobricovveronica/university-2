package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "marks", schema = "university")
public class Mark {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "value")
    private Double value;

    @Column(name = "create_data")
    private LocalDate createData;
}
