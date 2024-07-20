package com.sarobotics.carcounter.repository;

import org.springframework.data.repository.CrudRepository;

import com.sarobotics.carcounter.entity.Counter;

public interface CounterRepository extends CrudRepository<Counter, Long> {

}
