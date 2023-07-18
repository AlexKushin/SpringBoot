package com.shpp.mentoring.okushin.springboot.validatorTest;

import com.shpp.mentoring.okushin.springboot.validator.IpnValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@RunWith(SpringRunner.class)
class IpnValidatorTest extends IpnValidator {


    @ParameterizedTest
    @CsvSource({
            "3419005370",

    })
    void testIsValidIpn(String ipn) {
        IpnValidator validator = new IpnValidator();
        assertTrue(validator.isValidIpn(ipn));
    }
    @ParameterizedTest
    @CsvSource({
            "3419005310",
            "3419005110",
            "3419005410"
    })
    void testIsNotValidIpn(String ipn) {
        IpnValidator validator = new IpnValidator();
        assertFalse(validator.isValidIpn(ipn));
    }
}