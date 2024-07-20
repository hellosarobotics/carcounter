package com.sarobotics.carcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarobotics.carcounter.entity.Counter;
import com.sarobotics.carcounter.repository.CounterRepository;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Transactional
    public int incrementCounter(Long id) {
        Counter counter = counterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Counter with id " + id + " not found"));

        int updatedValue = counter.getContatore() + 1;
        counter.setContatore(updatedValue);
        counterRepository.save(counter);

        return updatedValue;
    }

    @Transactional
	public int resetCounter(Long id, int value) {
    	 Counter counter = counterRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Counter with id " + id + " not found"));

         int updatedValue = value;
         counter.setContatore(updatedValue);
         counterRepository.save(counter);

         return updatedValue;
	}
}