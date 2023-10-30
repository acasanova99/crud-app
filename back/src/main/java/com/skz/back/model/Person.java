package com.skz.back.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "person",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "passwordSalt")
    private String passwordSalt;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    public Person() {
    }

    public Person(String name, String surname, String password, String email, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("password='" + password + "'")
                .add("passwordSalt='" + passwordSalt + "'")
                .add("email='" + email + "'")
                .add("birthday=" + birthday)
                .toString();
    }
}
