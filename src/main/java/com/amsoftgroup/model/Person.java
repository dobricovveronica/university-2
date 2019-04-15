package com.amsoftgroup.model;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persons", schema = "university")
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name", length = 20)
  private String firstName;

  @Column(name = "last_name", length = 20)
  private String lastName;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "gender")
  private Character gender;

  @Column(name = "picture")
  private byte[] picture;

  @Column(name = "mail")
  private String mail;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToOne
  @JoinColumn(name = "library_abonament_id")
  private LibraryAbonament libraryAbonament;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "university.persons_to_phones", joinColumns = {
          @JoinColumn(name = "person_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "phone_id", referencedColumnName = "id")})
  private Set<Phone> phones = new HashSet<>();

  public Person() {
  }
}
