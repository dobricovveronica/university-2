package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "marks", schema = "university")
public class Mark {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "value")
    private Double value;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_data")
    private LocalDate createData;
}
