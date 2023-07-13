package com.shpp.mentoring.okushin.springboot.controller;

import com.shpp.mentoring.okushin.springboot.service.DataLoader;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

   Iterable<PersonEntity> get(){
        return loader.getUsers();
    }
    @GetMapping("/{ipn}")
    Optional<PersonEntity> getUserByIpn(@PathVariable String ipn){
        return loader.getUserByIpn(ipn);
    }
    @PostMapping
    PersonEntity postUser(@RequestBody @Valid PersonEntity person) {
        return loader.postUser(person);
    }
    @PutMapping("/{ipn}")
    void putUser(@PathVariable  String ipn,
                  @RequestBody @Valid PersonEntity person){
        loader.putUser(ipn, person);
    }

    @DeleteMapping("/{ipn}")
    void deleteUser(@PathVariable String ipn){
        loader.deleteUser(ipn);
    }

}
