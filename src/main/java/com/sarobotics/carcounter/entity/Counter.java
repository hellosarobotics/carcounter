package com.sarobotics.carcounter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Counter {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int contatore;
	
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getContatore() {
		return contatore;
	}
	public void setContatore(int contatore) {
		this.contatore = contatore;
	}
    
    

}
