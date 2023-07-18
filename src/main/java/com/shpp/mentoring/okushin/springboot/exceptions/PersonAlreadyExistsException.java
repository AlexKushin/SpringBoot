package com.shpp.mentoring.okushin.springboot.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException(String ipn) {
        super("Person assigned with ipn = " + ipn + " has already existed in repository.");
    }
}
