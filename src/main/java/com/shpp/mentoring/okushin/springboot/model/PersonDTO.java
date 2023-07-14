package com.shpp.mentoring.okushin.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String ipn;
    private String firstName;
    private String lastName;
}
