package com.shpp.mentoring.okushin.springboot.service;

import com.shpp.mentoring.okushin.springboot.converter.PersonConverter;
import com.shpp.mentoring.okushin.springboot.exceptions.EntityNotFoundException;
import com.shpp.mentoring.okushin.springboot.exceptions.PersonAlreadyExistsException;
import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import com.shpp.mentoring.okushin.springboot.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j

public class DataLoader {
    private final PersonRepository personRepository;

    public DataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
        log.info("Data loader was created");
    }

    public Iterable<PersonDTO> getUsers() {
        log.info("List of Persons is returned");
        return PersonConverter
                .convertEntityListToDtoList(personRepository.findAll());

    }

    public PersonDTO getUserByIpn(String ipn) {
        if (!personRepository.existsById(ipn)) {
            log.error("No any Person by assigned ipn = {}", ipn);
            throw new EntityNotFoundException(ipn);
        }
        return PersonConverter.convertEntityToDto(personRepository.findById(ipn).get());
    }

    public PersonDTO postUser(PersonDTO personDTO) {
        if (!personRepository.existsById(personDTO.getIpn())) {
            PersonEntity person = PersonConverter.convertDtoToEntity(personDTO);
            personRepository.save(person);
            log.info("new Person was written to repository. Person: first name = {}, last name = {}, ipn = {}",
                    person.getFirstName(), person.getLastName(), person.getIpn());
            return personDTO;
        }
        log.info("Person assigned with ipn = {} has already existed in repository. " +
                        "Person: first name = {}, last name = {}, ipn = {}",
                personDTO.getIpn(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getIpn());
        throw new PersonAlreadyExistsException(personDTO.getIpn());

    }

    public ResponseEntity<PersonDTO> putUser(String ipn, PersonDTO personDTO) {
        PersonEntity personEntity = PersonConverter.convertDtoToEntity(personDTO);
        log.info("Try to put Person to repository, Person: first name = {}, last name = {}, ipn = {}",
                personEntity.getFirstName(), personEntity.getLastName(), personEntity.getIpn());


        return (personRepository.existsById(ipn)) ? new ResponseEntity<>(PersonConverter
                        .convertEntityToDto(personRepository.save(personEntity)), HttpStatus.OK)
                : new ResponseEntity<>(PersonConverter
                .convertEntityToDto(personRepository.save(personEntity)), HttpStatus.CREATED);
    }

    public PersonDTO deleteUser(String ipn) {
        Optional<PersonEntity> person = personRepository.findById(ipn);
        if (person.isEmpty()) {
            log.error("No any Person by assigned ipn = {}", ipn);
            throw new EntityNotFoundException(ipn);
        }
        log.info("Try to delete Person by ipn {} from repository", ipn);
        personRepository.deleteById(ipn);
        log.info("Person assigned by ipn {} was deleted from repository", ipn);
        return PersonConverter.convertEntityToDto(person.get());
    }
}
