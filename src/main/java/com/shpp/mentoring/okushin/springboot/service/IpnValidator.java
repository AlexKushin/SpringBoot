package com.shpp.mentoring.okushin.springboot.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Service
public class IpnValidator {


    boolean isValidIpn(String ipn) {
        String dateOfBirth = ipn.substring(0, 5);
        long daysBeforeToday = ChronoUnit.DAYS.between(LocalDate.of(1899, 12, 31), LocalDate.now());

        if (Long.parseLong(dateOfBirth) > daysBeforeToday) {
            return false;
        }
        int[] coefficients = {-1, 5, 7, 9, 4, 6, 10, 5, 7};
        int sum = 0;

        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Integer.parseInt(String.valueOf(ipn.charAt(i)));
        }
        int remainder = sum % 11;
        int checkDigit = remainder == 10 ? 0 : remainder;
        return checkDigit == Integer.parseInt(String.valueOf(ipn.charAt(9)));
    }
}
