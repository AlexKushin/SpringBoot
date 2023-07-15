package com.shpp.mentoring.okushin.springboot.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String ipn) {
        super("Entity is not found, ipn=" + ipn);
    }
}
