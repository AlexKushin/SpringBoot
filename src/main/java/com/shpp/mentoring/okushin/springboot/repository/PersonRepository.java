package com.shpp.mentoring.okushin.springboot.repository;

import com.shpp.mentoring.okushin.springboot.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {
}
