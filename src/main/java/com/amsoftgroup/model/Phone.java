package com.amsoftgroup.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "phones", schema = "university")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PhoneType phoneType;

    @Column(name = "value")
    private String value;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "university.persons_to_phones", joinColumns = {@JoinColumn(name = "phone_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")})
    private Set<Person> persons = new HashSet<>();

}
