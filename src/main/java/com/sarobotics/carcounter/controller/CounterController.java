package com.sarobotics.carcounter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarobotics.carcounter.dto.EventiPerGiornoDTO;
import com.sarobotics.carcounter.dto.EventiPerOraDTO;
import com.sarobotics.carcounter.entity.Counter;
import com.sarobotics.carcounter.repository.CounterRepository;
import com.sarobotics.carcounter.service.CounterService;
import com.sarobotics.carcounter.service.LogEventiService;

@RestController
@RequestMapping("/counter")
public class CounterController {
	
	 	@Autowired
	    private CounterRepository counterRepository;
	 	@Autowired
	    private CounterService counterService;
	 	
	 	@Autowired
	 	private LogEventiService les;

	 
	 	//@PostMapping
	    public Counter addCounter(@RequestBody Counter c) {
	        return counterRepository.save(c);
	    }

	    @GetMapping("/{id}")
	    public Optional<Counter> getCounter(@PathVariable Long id) {
	        return counterRepository.findById(id);
	    }

	    @GetMapping
	    public Iterable<Counter> getAllCounters() {
	        return counterRepository.findAll();
	    }
	    
	    //@PutMapping("/{id}/increment")
	    public ResponseEntity<?> incrementCounter(@PathVariable Long id) {
	        int updatedValue = counterService.incrementCounter(id);
	        return ResponseEntity.ok("Counter with ID " + id + " incremented. New value: " + updatedValue);
	    }
	    
	    //@PutMapping("/{id}/reset/{value}")
	    public ResponseEntity<?> resetCounter(@PathVariable Long id, @PathVariable int value) {
	        int updatedValue = counterService.resetCounter(id, value);
	        return ResponseEntity.ok("Counter with ID " + id + " resetted. New value: " + updatedValue);
	    }
	    
	    
	    @GetMapping("/eventiPerOra")
	    public List<EventiPerOraDTO> countEventiPerOra(@RequestParam String data) {
	        return les.countEventiPerOra(data);
	    }
	    
//	    @GetMapping("/counter/eventiPerGiorno")
//	    public EventiPerGiornoDTO getEventiPerGiorno(@RequestParam String data) {
//	        return les.countEventiPerGiorno(data);
//	    }
	    
	    @GetMapping("/eventiPerGiorno")
	    public ResponseEntity<EventiPerGiornoDTO> getEventiPerGiorno(@RequestParam String data) {
	        EventiPerGiornoDTO eventiPerGiornoDTO = les.countEventiPerGiorno(data);
	        return ResponseEntity.ok(eventiPerGiornoDTO);
	    }
	    
	    @GetMapping("/totaleUltimi7Giorni")
	    public List<EventiPerGiornoDTO> countTotaleEventiDegliUltimi7Giorni() {
	        return les.countTotaleEventiDegliUltimi7Giorni();
	    }
	    
	    @GetMapping("/totaleUltimi14Giorni")
	    public List<EventiPerGiornoDTO> countTotaleEventiDegliUltimi14Giorni() {
	        return les.countTotaleEventiDegliUltimi14Giorni();
	    }
	    
}
