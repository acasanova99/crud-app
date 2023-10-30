package com.skz.back.repository;

import com.skz.back.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(final String email);
    void deleteByEmail(final String email);
}
