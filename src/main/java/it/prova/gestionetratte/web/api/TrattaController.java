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
import it.prova.raccoltafilmspringrest.dto.FilmDTO;
import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.web.api.exception.FilmNotFoundException;

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
	public FilmDTO findById(@PathVariable(value = "id", required = true) long id) {
		Film film = filmService.caricaSingoloElementoEager(id);

		if (film == null)
			throw new FilmNotFoundException("Film not found con id: " + id);

		return FilmDTO.buildFilmDTOFromModel(film, true);
	}

	@PostMapping("/search")
	public List<FilmDTO> search(@RequestBody FilmDTO example) {
		return FilmDTO.createFilmDTOListFromModelList(filmService.findByExample(example.buildFilmModel()), false);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true) Long id) {
		Film film = filmService.caricaSingoloElemento(id);

		if (film == null)
			throw new FilmNotFoundException("film not found con id: " + id);

		filmService.rimuovi(film);
	}

	@PutMapping("/{id}")
	public FilmDTO update(@Valid @RequestBody FilmDTO filmInput, @PathVariable(required = true) Long id) {
		Film film = filmService.caricaSingoloElemento(id);

		if (film == null)
			throw new FilmNotFoundException("film not found con id: " + id);

		filmInput.setId(id);
		Film filmAggiornato = filmService.aggiorna(filmInput.buildFilmModel());
		return FilmDTO.buildFilmDTOFromModel(filmAggiornato, false);
	}
	
}
