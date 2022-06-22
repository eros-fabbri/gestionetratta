package it.prova.gestionetratte.service;

import java.util.List;

import it.prova.gestionetratte.model.Tratta;

public interface TrattaService {
	
	List<Tratta> listAll();

	Tratta caricaSingoloElemento(Long id);

	Tratta caricaSingoloElementoEager(Long id);

	Tratta aggiorna(Tratta trattaInput);

	Tratta inserisci(Tratta trattaInput);

	void rimuovi(Tratta trattaInput);
}
