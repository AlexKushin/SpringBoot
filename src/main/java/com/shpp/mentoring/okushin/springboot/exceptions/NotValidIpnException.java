package com.shpp.mentoring.okushin.springboot.exceptions;

public class NotValidIpnException extends RuntimeException {
    public NotValidIpnException(String ipn) {
        super("Ipn is not valid ipn= " + ipn);
    }
}
