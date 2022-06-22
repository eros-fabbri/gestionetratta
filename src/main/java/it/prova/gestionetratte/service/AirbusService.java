package it.prova.gestionetratte.service;

import java.util.List;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusService {

	List<Airbus> listAll();

	Airbus caricaSingoloElemento(Long id);

	Airbus caricaSingoloElementoEager(Long id);
	
	Airbus aggiorna(Airbus airbusInput);
	
	Airbus inserisci(Airbus airbusInput);
	
	void rimuovi(Airbus airbusInput);
	
	
	

}
