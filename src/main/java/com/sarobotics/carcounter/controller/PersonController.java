package com.sarobotics.carcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sarobotics.carcounter.entity.Person;
import com.sarobotics.carcounter.repository.PersonRepository;

import java.util.Optional;

//@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping("/{id}")
    public Optional<Person> getPerson(@PathVariable Long id) {
        return personRepository.findById(id);
    }

    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }
}