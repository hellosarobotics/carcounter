package com.sarobotics.carcounter.repository;

import org.springframework.data.repository.CrudRepository;

import com.sarobotics.carcounter.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}