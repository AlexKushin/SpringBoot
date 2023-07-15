package com.shpp.mentoring.okushin.springboot.controller;

import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import com.shpp.mentoring.okushin.springboot.service.DataLoader;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@ApiResponses
public class RestApiUserController {
    private final DataLoader loader;

    public RestApiUserController(DataLoader loader) {
        this.loader = loader;
    }

    @GetMapping
    Iterable<PersonEntity> get() {
        return loader.getUsers();
    }

    @GetMapping("/{ipn}")
    Optional<PersonEntity> getUserByIpn(@PathVariable String ipn) {
        return loader.getUserByIpn(ipn);
    }

    @PostMapping
    PersonDTO postUser(@RequestBody @Valid PersonDTO personDTO) {
        return loader.postUser(personDTO);
    }

    @PutMapping("/{ipn}")
    ResponseEntity<PersonEntity> putUser(@PathVariable String ipn, @RequestBody @Valid PersonDTO personDTO) {
        return loader.putUser(ipn, personDTO);
    }

    @DeleteMapping("/{ipn}")
    void deleteUser(@PathVariable String ipn) {
        loader.deleteUser(ipn);
    }

}
