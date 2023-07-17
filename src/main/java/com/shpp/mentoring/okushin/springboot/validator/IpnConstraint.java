package com.shpp.mentoring.okushin.springboot.validator;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpnValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IpnConstraint {
    String message() default "Invalid ipn (Taxpayer identifier number)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
