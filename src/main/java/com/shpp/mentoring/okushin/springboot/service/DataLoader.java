package com.shpp.mentoring.okushin.springboot.service;

import com.shpp.mentoring.okushin.springboot.converter.PersonConverter;
import com.shpp.mentoring.okushin.springboot.exceptions.EntityNotFoundException;
import com.shpp.mentoring.okushin.springboot.exceptions.NotValidIpnException;
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

    private final PersonConverter customerConverter;

    public DataLoader(PersonRepository personRepository, PersonConverter customerConverter) {
        this.personRepository = personRepository;
        this.customerConverter = customerConverter;
        log.info("Data loader was created");
    }

    public Iterable<PersonEntity> getUsers() {
        log.info("List of Persons is returned");
        //якщо пусто то 204
        return personRepository.findAll();
    }

    public Optional<PersonEntity> getUserByIpn(String ipn) {
        Optional<PersonEntity> person = personRepository.findById(ipn);
        if (person.isEmpty()) {
            log.error("No any Person by assigned ipn = {}", ipn);
            throw new EntityNotFoundException(ipn);
        }
        return personRepository.findById(ipn);
    }

    public ResponseEntity<PersonDTO> postUser(PersonDTO personDTO) {
        Optional<PersonEntity> personInRepo = personRepository.findById(personDTO.getIpn());
        if (personInRepo.isEmpty()) {
            PersonEntity person = customerConverter.convertDtoToEntity(personDTO);
            personRepository.save(person);
            log.info("new Person was written to repository. Person: first name = {}, last name = {}, ipn = {}",
                    person.getFirstName(), person.getLastName(), person.getIpn());
            return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
        }
        log.info("Person assigned with ipn = {} has already existed in repository. " +
                        "Person: first name = {}, last name = {}, ipn = {}",
                personDTO.getIpn(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getIpn());
        throw new PersonAlreadyExistsException(personDTO.getIpn());

    }

    public ResponseEntity<PersonDTO> putUser(String ipn, PersonDTO personDTO) {
        PersonEntity person = customerConverter.convertDtoToEntity(personDTO);
        person = personRepository.save(person);
        log.info("Try to put Person to repository, Person: first name = {}, last name = {}, ipn = {}",
                person.getFirstName(), person.getLastName(), person.getIpn());
        return (personRepository.existsById(ipn)) ?
                new ResponseEntity<>(customerConverter
                        .convertEntityToDto(personRepository.save(person)), HttpStatus.OK)
                : new ResponseEntity<>(customerConverter
                .convertEntityToDto(personRepository.save(person)), HttpStatus.CREATED);
    }

    public ResponseEntity<PersonEntity> deleteUser(String ipn) {
        Optional<PersonEntity> person = personRepository.findById(ipn);
        if (person.isEmpty()) {
            log.error("No any Person by assigned ipn = {}", ipn);
            throw new EntityNotFoundException(ipn);
        }
        log.info("Try to delete Person by ipn {} from repository", ipn);
        personRepository.deleteById(ipn);
        log.info("Person assigned by ipn {} was deleted from repository", ipn);
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }
}
