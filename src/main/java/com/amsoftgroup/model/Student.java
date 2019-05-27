package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "students", schema = "university")
public class Student extends Person {


    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "university.disciplines_to_students", joinColumns = {
            @JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "discipline_id", referencedColumnName = "id")})
    private Set<Discipline> disciplines = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Average> averages = new ArrayList<>();

    public Student() {
    }

    public Double studentAverage() {
        Double sum = 0D;
        for (Average avg : averages) {
            sum += avg.getValue();
        }
        if (averages.size() != 0) {
            return sum / averages.size();
        } else return 0D;
    }
}
