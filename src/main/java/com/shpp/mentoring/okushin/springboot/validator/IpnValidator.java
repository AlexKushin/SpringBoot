package com.shpp.mentoring.okushin.springboot.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Slf4j
public class IpnValidator implements ConstraintValidator<IpnConstraint, String> {


    public boolean isValidIpn(String ipn) {
        String dateOfBirth = ipn.substring(0, 5);
        long daysBeforeToday = ChronoUnit.DAYS.between(LocalDate.of(1899, 12, 31), LocalDate.now());

        if (Long.parseLong(dateOfBirth) > daysBeforeToday) {
            log.error("Given ipn {} is not valid", ipn);
            return false;
        }
        return validCheckDigit(ipn);
    }

    private boolean validCheckDigit(String ipn) {
        int[] coefficients = {-1, 5, 7, 9, 4, 6, 10, 5, 7};
        int sum = 0;

        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Integer.parseInt(String.valueOf(ipn.charAt(i)));
        }
        int remainder = sum % 11;
        int checkDigit = remainder == 10 ? 0 : remainder;
        if (checkDigit != Integer.parseInt(String.valueOf(ipn.charAt(9)))) {
            log.error("Check digit in given ipn is not valid");
            log.error("Given ipn is not valid");
            return false;
        } else {
            log.info("Ipn was validated successfully");
            return true;
        }
    }

    @Override
    public void initialize(IpnConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isValidIpn(s);
    }
}
