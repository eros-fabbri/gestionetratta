package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.TrattaService;
import it.prova.gestionetratte.web.exception.IdNotNullForInsertException;
import it.prova.gestionetratte.web.exception.TrattaNotFoundException;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {
	
	@Autowired
	TrattaService trattaService;
	
	@GetMapping
	public List<TrattaDTO> getAll() {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAll());
	}
	
	@PostMapping
	public TrattaDTO createNew(@Valid @RequestBody TrattaDTO trattaInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (trattaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Tratta trattaInserita = trattaService.inserisci(trattaInput.buildTrattaModel());
		return TrattaDTO.createTrattaDTOFromModel(trattaInserita);
	}
	
	@GetMapping("/{id}")
	public TrattaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Tratta tratta = trattaService.caricaSingoloElementoEager(id);

		if (tratta == null)
			throw new TrattaNotFoundException("Tratta not found con id: " + id);

		return TrattaDTO.createTrattaDTOFromModel(tratta);
	}

	@PostMapping("/search")
	public List<TrattaDTO> search(@RequestBody TrattaDTO example) {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.findByExample(example.buildTrattaModel()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true) Long id) {
		Tratta tratta = trattaService.caricaSingoloElemento(id);

		if (tratta == null)
			throw new TrattaNotFoundException("tratta not found con id: " + id);

		trattaService.rimuovi(tratta);
	}

	@PutMapping("/{id}")
	public TrattaDTO update(@Valid @RequestBody TrattaDTO trattaInput, @PathVariable(required = true) Long id) {
		Tratta tratta = trattaService.caricaSingoloElemento(id);

		if (tratta == null)
			throw new TrattaNotFoundException("tratta not found con id: " + id);

		trattaInput.setId(id);
		Tratta trattaAggiornato = trattaService.aggiorna(trattaInput.buildTrattaModel());
		return TrattaDTO.createTrattaDTOFromModel(trattaAggiornato);
	}
	@GetMapping("/concludiTratta")
	public void concludiTratta(@PathVariable(value = "id", required = true) long id) {
		trattaService.concludiTratte();
	}
	
}
