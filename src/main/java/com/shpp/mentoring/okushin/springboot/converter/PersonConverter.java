package com.shpp.mentoring.okushin.springboot.converter;

import com.shpp.mentoring.okushin.springboot.model.PersonDTO;
import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    public static PersonDTO convertEntityToDto(PersonEntity person) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(person, PersonDTO.class);
    }

    public static PersonEntity convertDtoToEntity(PersonDTO personDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personDTO, PersonEntity.class);
    }

    public static List<PersonDTO> convertEntityListToDtoList(List<PersonEntity> personEntitiesList) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        if (personEntitiesList.iterator().hasNext()) {
            for (PersonEntity entity : personEntitiesList) {
                personDTOList.add(PersonConverter.convertEntityToDto(entity));
            }
        }
        return personDTOList;
    }
}
