package it.prova.gestionetratte.web.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.AirbusSovrapposizioneDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.web.exception.AirbusNotFoundException;
import it.prova.gestionetratte.web.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

	@Autowired
	private AirbusService airbusService;

	@GetMapping
	public List<AirbusDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllEager(), true);
	}

	@GetMapping("/{id}")
	public AirbusDTO findById(@PathVariable(value = "id", required = true) long id) {
		Airbus airbus = airbusService.caricaSingoloElementoEager(id);

		if (airbus == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		return AirbusDTO.buildAirbusDTOFromModel(airbus);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AirbusDTO createNew(@Valid @RequestBody AirbusDTO airbusInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (airbusInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Airbus airbusInserito = airbusService.inserisci(airbusInput.buildAirbusModel());
		return AirbusDTO.buildAirbusDTOFromModel(airbusInserito);
	}

	@PutMapping("/{id}")
	public AirbusDTO update(@Valid @RequestBody AirbusDTO airbusInput, @PathVariable(required = true) Long id) {
		Airbus airbus = airbusService.caricaSingoloElemento(id);

		if (airbus == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		airbusInput.setId(id);
		Airbus airbusAggiornato = airbusService.aggiorna(airbusInput.buildAirbusModel());
		return AirbusDTO.buildAirbusDTOFromModel(airbusAggiornato);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Airbus airbus = airbusService.caricaSingoloElemento(id);

		if (airbus == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		airbusService.rimuovi(airbus);
	}

	@PostMapping("/search")
	public List<AirbusDTO> search(@RequestBody AirbusDTO example) {
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.findByExample(example.buildAirbusModel()),
				false);
	}

	@GetMapping("/listaAirbusEvidenziandoSovrapposizioni ")
	public List<AirbusSovrapposizioneDTO> listaAirbusEvidenziandoSovrapposizioni() {
		return Stream.concat(AirbusSovrapposizioneDTO
				.buildAirbusSovrappostoListFromAirbusList(airbusService.listConSovrapposizione(), true).stream(),
				AirbusSovrapposizioneDTO
						.buildAirbusSovrappostoListFromAirbusList(airbusService.listSenzaSovrapposizione(), false)
						.stream())
				.collect(Collectors.toList());
	}
}
