package com.sarobotics.carcounter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarobotics.carcounter.dto.EventiPerGiornoDTO;
import com.sarobotics.carcounter.dto.EventiPerOraDTO;
import com.sarobotics.carcounter.entity.LogEventi;
import com.sarobotics.carcounter.repository.LogEventiRepository;

@Service
public class LogEventiService {

    @Autowired
    private LogEventiRepository ler;

    @Transactional
    public LogEventi insetNewEvent(double decibel) {
    	
    	LogEventi le = new LogEventi();
    	le.setDecibel(decibel);
       
        LogEventi salvato = ler.save(le);

        return salvato;
    }

    public List<EventiPerOraDTO> countEventiPerOra(String data) {
    	List<Object[]> results = ler.countEventiPerOra(data);
        List<EventiPerOraDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            String giorno = Objects.toString(result[0], null);
            int ora = (Integer) result[1];
            Integer numeroEventi = (Integer) result[2];
            double macchineAlMinuto = (Double) result[3];

            EventiPerOraDTO dto = new EventiPerOraDTO(giorno, ora, numeroEventi, macchineAlMinuto);
            dtos.add(dto);
        }

        return dtos;
    }

	public List<EventiPerGiornoDTO> countTotaleEventiDegliUltimi7Giorni() {
		List<Object[]> results = ler.eventiPerGiorno();
        List<EventiPerGiornoDTO> epgList = new ArrayList<>();
        
        // converter
        for (Object[] result : results) {
            String giorno = Objects.toString(result[0], null);
            Integer numeroEventi = (Integer) result[1];
            double macchineAlMinuto = (Double) result[2];

            EventiPerGiornoDTO dto = new EventiPerGiornoDTO(giorno, numeroEventi, macchineAlMinuto);
            epgList.add(dto);
        }

        return epgList;
	}

	public List<EventiPerGiornoDTO> countTotaleEventiDegliUltimi14Giorni() {
		List<Object[]> results = ler.eventiPerGiorno();
        List<EventiPerGiornoDTO> epgList = new ArrayList<>();
        
        // converter
        for (Object[] result : results) {
            String giorno = Objects.toString(result[0], null);
            Integer numeroEventi = (Integer) result[1];
            double macchineAlMinuto = (Double) result[2];

            EventiPerGiornoDTO dto = new EventiPerGiornoDTO(giorno, numeroEventi, macchineAlMinuto);
            epgList.add(dto);
        }

        return epgList;
	}

	 public EventiPerGiornoDTO countEventiPerGiorno(String data) {
	        int numeroEventi = ler.countByGiorno(data);
	        EventiPerGiornoDTO eventiPerGiornoDTO = new EventiPerGiornoDTO();
	        eventiPerGiornoDTO.setGiorno(data);
	        eventiPerGiornoDTO.setNumeroEventi(numeroEventi);
	        return eventiPerGiornoDTO;
	    }
   
}