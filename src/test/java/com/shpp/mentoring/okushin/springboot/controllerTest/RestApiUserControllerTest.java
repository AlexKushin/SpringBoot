package com.shpp.mentoring.okushin.springboot.controllerTest;

import com.shpp.mentoring.okushin.springboot.controller.RestApiUserController;
import com.shpp.mentoring.okushin.springboot.converter.PersonConverter;
import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import com.shpp.mentoring.okushin.springboot.repository.PersonRepository;
import com.shpp.mentoring.okushin.springboot.service.DataLoader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestApiUserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RestApiUserControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    DataLoader dataLoader;
    @MockBean
    PersonRepository repository;
    @MockBean
    PersonConverter converter;
    @MockBean
    ResponseEntity<PersonDTO> responseEntity;

    @Test
    void testGet() throws Exception {
        PersonDTO p1 = new PersonDTO("3419005370", "Jack", "London");
        PersonDTO p2 = new PersonDTO("0000000002", "Paul", "Silver");
        ResponseEntity<Iterable<PersonDTO>> personList = new ResponseEntity<>(List.of(p1, p2), HttpStatus.OK);
        given(dataLoader.getUsers()).willReturn(personList);

        mvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(p1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName").value(p1.getLastName()))
                .andExpect(jsonPath("$[0].ipn").value(p1.getIpn()))
                .andExpect(jsonPath("$[1].firstName", is(p2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName").value(p2.getLastName()))
                .andExpect(jsonPath("$[1].ipn").value(p2.getIpn()));

        verify(dataLoader, VerificationModeFactory.times(1)).getUsers();

    }

    @Test
    void testGetUserByIpn() throws Exception {

        PersonDTO alex = new PersonDTO("3419005730", "Alex", "Kushyn");
       // PersonConverter converter = new PersonConverter();
        given(dataLoader.getUserByIpn("3419005730")).willReturn(alex);

        mvc.perform(get("/persons/3419005730").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alex)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastName").value("Kushyn"));

        verify(dataLoader, VerificationModeFactory.times(1)).getUserByIpn(Mockito.any());
    }

   @Test
    void testPostUser() throws Exception {
        PersonDTO alexDTO = new PersonDTO("3419005370", "Alex", "Kushyn");
        PersonEntity alexEntity = converter.convertDtoToEntity(alexDTO);
        ResponseEntity<PersonDTO> alexRE = new ResponseEntity<>(alexDTO, HttpStatus.CREATED);

        given(repository.save(alexEntity)).willReturn(alexEntity);
        given(dataLoader.postUser(alexDTO)).willReturn(alexRE);
        given(dataLoader.getUserByIpn("3419005370")).willReturn(alexDTO);

        mvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alexDTO)))
                .andExpect(status().is2xxSuccessful());

        mvc.perform(get("/persons/3419005370").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alexDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastName").value("Kushyn"))
                .andExpect(jsonPath("$.ipn").value("3419005370"));


        verify(dataLoader, VerificationModeFactory.times(1)).postUser(Mockito.any());

    }



    @Test
    void testPutUser() throws Exception {
        PersonDTO alexDTO = new PersonDTO("3419005370", "Alex", "Kushyn");
        PersonEntity alexEntity = converter.convertDtoToEntity(alexDTO);
        ResponseEntity<PersonDTO> alexRE = new ResponseEntity<>(alexDTO, HttpStatus.CREATED);

        given(repository.save(alexEntity)).willReturn(alexEntity);
        given(dataLoader.postUser(alexDTO)).willReturn(alexRE);
        given(dataLoader.getUserByIpn("3419005370")).willReturn(alexDTO);

        mvc.perform(put("/persons/3419005370").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alexDTO)))
                .andExpect(status().isOk());

        mvc.perform(get("/persons/3419005370").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alexDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastName").value("Kushyn"))
                .andExpect(jsonPath("$.ipn").value("3419005370"));


        verify(dataLoader, VerificationModeFactory.times(1)).putUser(Mockito.any(), Mockito.any());

    }

    @Test
    void testDeleteUser() throws Exception {
        mvc.perform(delete("/persons/3419005370").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(dataLoader, VerificationModeFactory.times(1)).deleteUser(Mockito.any());

    }
}