package com.shpp.mentoring.okushin.springboot.service;

import com.shpp.mentoring.okushin.springboot.converter.PersonConverter;
import com.shpp.mentoring.okushin.springboot.exceptions.EntityNotFoundException;
import com.shpp.mentoring.okushin.springboot.exceptions.NotValidIpnException;
import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import com.shpp.mentoring.okushin.springboot.repository.PersonRepository;
import com.shpp.mentoring.okushin.springboot.validator.IpnValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Slf4j
public class DataLoader {
    private final PersonRepository personRepository;
    private final IpnValidator validator;
    private final PersonConverter customerConverter;

    public DataLoader(PersonRepository personRepository, IpnValidator validator, PersonConverter customerConverter) {
        this.personRepository = personRepository;
        this.validator = validator;
        this.customerConverter = customerConverter;
        log.info("Data loader was created");
    }

    public Iterable<PersonEntity> getUsers() {
        log.info("List of Persons is returned");
        return personRepository.findAll();
    }

    public Optional<PersonEntity> getUserByIpn(String ipn) {
        if (!validator.isValidIpn(ipn)) {
            throw new NotValidIpnException(ipn);
        }
        Optional<PersonEntity> person = personRepository.findById(ipn);
        if (person.isEmpty()) {
            log.error("No any Person by assigned ipn");
            throw new EntityNotFoundException(ipn);
        }
        return personRepository.findById(ipn);
    }

    public PersonDTO postUser(@RequestBody PersonDTO personDTO) {
        String ipn = personDTO.getIpn();
        if (validator.isValidIpn(ipn)) {
            PersonEntity person = customerConverter.convertDtoToEntity(personDTO);
            person = personRepository.save(person);
            log.info("new Person was written to repository");
            return customerConverter.convertEntityToDto(person);
        }
        throw new NotValidIpnException(ipn);

    }

    public ResponseEntity<PersonEntity> putUser(String ipn,
                                                PersonDTO personDTO) {
        if (validator.isValidIpn(ipn)) {
            PersonEntity person = customerConverter.convertDtoToEntity(personDTO);
            person = personRepository.save(person);
            log.info("try to put Person to repository");
            return (personRepository.existsById(ipn)) ? new ResponseEntity<>(personRepository.save(person), HttpStatus.OK)
                    : new ResponseEntity<>(personRepository.save(person),
                    HttpStatus.CREATED);
        }

        throw new NotValidIpnException(ipn);
    }

    public void deleteUser(@PathVariable String ipn) {
        if (validator.isValidIpn(ipn)) {
            log.info("try to delete Person from repository");
            personRepository.deleteById(ipn);
        }
        throw new NotValidIpnException(ipn);
    }
}
