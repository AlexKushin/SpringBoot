package com.shpp.mentoring.okushin.springboot.controller;

import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.service.DataLoader;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
@ApiResponses
public class RestApiUserController {
    private final DataLoader loader;

    public RestApiUserController(DataLoader loader) {
        this.loader = loader;
    }

    @GetMapping
    ResponseEntity<Iterable<PersonDTO>> get() {
        return loader.getUsers();
    }

    @GetMapping("/{ipn}")
    PersonDTO getUserByIpn(@PathVariable @Valid String ipn) {
        return loader.getUserByIpn(ipn);
    }

    @PostMapping
    ResponseEntity<PersonDTO> postUser(@RequestBody @Valid PersonDTO personDTO) {
        return loader.postUser(personDTO);
    }

    @PutMapping("/{ipn}")
    ResponseEntity<PersonDTO> putUser(@PathVariable @Valid String ipn, @RequestBody @Valid PersonDTO personDTO) {
        return loader.putUser(ipn, personDTO);
    }

    @DeleteMapping("/{ipn}")
    ResponseEntity<PersonDTO> deleteUser(@PathVariable @Valid String ipn) {
        return loader.deleteUser(ipn);
    }

}
