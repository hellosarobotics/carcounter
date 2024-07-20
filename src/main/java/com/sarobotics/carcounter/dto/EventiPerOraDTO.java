package com.sarobotics.carcounter.dto;


public class EventiPerOraDTO {

    private String giorno;
    private int ora;
    private Integer numeroEventi;
    private double macchineAlMinuto;

    // costruttori, getter e setter
    
    public EventiPerOraDTO() {
    }

    public EventiPerOraDTO(String giorno, int ora, Integer numeroEventi, double macchineAlMinuto) {
        this.giorno = giorno;
        this.ora = ora;
        this.numeroEventi = numeroEventi;
        this.macchineAlMinuto = macchineAlMinuto;
    }

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public int getOra() {
		return ora;
	}

	public void setOra(int ora) {
		this.ora = ora;
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