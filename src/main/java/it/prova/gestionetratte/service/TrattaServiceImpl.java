package it.prova.gestionetratte.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;

@Service
public class TrattaServiceImpl implements TrattaService {

	@Autowired
	TrattaRepository trattaRepository;

	@Transactional
	@Override
	public List<Tratta> listAll() {
		return (List<Tratta>) trattaRepository.findAll();
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		return trattaRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		return trattaRepository.findSingleTrattaEager(id);
	}

	@Transactional
	@Override
	public Tratta aggiorna(Tratta trattaInput) {
		return trattaRepository.save(trattaInput);
	}

	@Transactional
	@Override
	public Tratta inserisci(Tratta trattaInput) {
		return trattaRepository.save(trattaInput);
	}

	@Transactional
	@Override
	public void rimuovi(Tratta trattaInput) {
		trattaRepository.delete(trattaInput);
	}

	@Override
	public List<Tratta> findByExample(Tratta tratta) {
		return trattaRepository.findByExample(tratta);
	}

	@Override
	@Transactional
	public void concludiTratte() {
		 trattaRepository.concludiTratte(LocalTime.now());
	}

}
