package com.sarobotics.carcounter.dto;


public class EventiPerGiornoDTO {

    private String giorno;
    private Integer numeroEventi;
    private double macchineAlMinuto;

    // costruttori, getter e setter
    
    public EventiPerGiornoDTO() {
    }

    public EventiPerGiornoDTO(String giorno, Integer numeroEventi, double macchineAlMinuto) {
        this.giorno = giorno;
        this.numeroEventi = numeroEventi;
        this.macchineAlMinuto = macchineAlMinuto;
    }

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public Integer getNumeroEventi() {
		return numeroEventi;
	}

	public void setNumeroEventi(Integer numeroEventi) {
		this.numeroEventi = numeroEventi;
	}

	public double getMacchineAlMinuto() {
		return macchineAlMinuto;
	}

	public void setMacchineAlMinuto(double macchineAlMinuto) {
		this.macchineAlMinuto = macchineAlMinuto;
	}

    
}