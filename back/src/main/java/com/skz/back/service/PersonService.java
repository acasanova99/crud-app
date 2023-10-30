package com.skz.back.service;


import com.skz.back.dto.PersonDto;
import com.skz.back.exception.BackendException;
import com.skz.back.model.Person;
import com.skz.back.repository.PersonRepository;
import com.skz.back.util.CryptoUtils;
import com.skz.back.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private static final int SALT_SIZE = 16;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ModelMapper mapper;

    public List<PersonDto> getAll() {
        LOGGER.info(String.format("%s::getAll()", this.getClass().getSimpleName()));

        return personRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, PersonDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonDto save(final PersonDto personDto) {
        LOGGER.info(String.format("%s::save(PersonDto): %s", this.getClass().getSimpleName(), personDto));

        return Optional
                .ofNullable(mapper.map(personDto, Person.class))
                .map(this::validate)
                .map(this::updatePasswd)
                .map(personRepository::save)
                .map(x -> mapper.map(personDto, PersonDto.class))
                .orElse(null);

    }

    public PersonDto get(final Long id) {
        LOGGER.info(String.format("%s::get(Long): %s", this.getClass().getSimpleName(), id));

        return personRepository.findById(id)
                .map(p -> mapper.map(p, PersonDto.class))
                .orElse(null);
    }

    public PersonDto getByEmail(final String email) {
        LOGGER.info(String.format("%s::getByEmail(String): %s", this.getClass().getSimpleName(), email));

        return personRepository.findByEmail(email)
                .map(p -> mapper.map(p, PersonDto.class))
                .orElse(null);
    }

    @Transactional
    public PersonDto update(final String oldEmail, final PersonDto personDto) {
        LOGGER.info(String.format("%s::update(PersonDto): %s", this.getClass().getSimpleName(), personDto));

        final Person newEntity = mapper.map(personDto, Person.class);

        return personRepository.findByEmail(oldEmail)
                .map(dbEntity -> {
                    EntityUtils.copyNonNullAttributes(newEntity, dbEntity, Person.class);
                    return dbEntity;
                })
                .map(this::validate)
                .map(this::updatePasswd)
                .map(personRepository::save)
                .map(x -> this.getByEmail(x.getEmail()))
                .orElse(null);
    }

    @Transactional
    public void delete(final Long id) {
        LOGGER.info(String.format("%s::delete(Long): %s", this.getClass().getSimpleName(), id));

        personRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEmail(final String email) {
        LOGGER.info(String.format("%s::deleteByEmail(String): %s", this.getClass().getSimpleName(), email));

        personRepository.deleteByEmail(email);
    }

    private Person updatePasswd(final Person person) {
        person.setPasswordSalt(CryptoUtils.generateRandomSalt(SALT_SIZE));
        person.setPassword(CryptoUtils.hashWithPepper(person.getPasswordSalt(), person.getPassword()));
        return person;
    }

    private Person validate(final Person p) {
        if (!EntityUtils.containsOnlyNonNumeric.test(p.getName() + p.getSurname())) {
            throw new BackendException("Name or Surname can only contain characters");
        }
        if (!EntityUtils.isStandardEmail.test(p.getEmail())) {
            throw new BackendException("Email do not follow a regular pattern");
        }
        if (!EntityUtils.isStrongPassword.test(p.getPassword())) {
            throw new BackendException("Password is not strong enough");
        }
        if (!EntityUtils.isMoreThan14YearsAgo.test(p.getBirthday())) {
            throw new BackendException("User is underage for register in the app");
        }
        return p;
    }
}
