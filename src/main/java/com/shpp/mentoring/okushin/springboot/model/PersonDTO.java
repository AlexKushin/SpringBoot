package com.shpp.mentoring.okushin.springboot.model;

import com.shpp.mentoring.okushin.springboot.validator.IpnConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @Id
    @Size(min = 10, max = 10)
    @Pattern(regexp = "([0-9.]*)")
    @IpnConstraint
    private String ipn;
    //@Pattern(regexp = ".*a.*")
    private String firstName;
    //@Pattern(regexp = ".*a.*")
    private String lastName;
}
