package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;

public class TrattaDTO {

	@NotNull
	private Long id;
	@NotBlank
	private String codice;
	@NotBlank
	private String descrizione;
	@NotNull
	private LocalDate data;
	@NotNull
	private LocalTime oraDecollo;
	@NotNull
	private LocalTime oraAtterraggio;
	@NotNull
	private StatoTratta stato;
	@NotNull
	private Airbus airbus;

	public TrattaDTO(Long id, String codice, String descrizione, LocalDate data, LocalTime oraDecollo,
			LocalTime oraAtterraggio, StatoTratta stato, Airbus airbus) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
		this.oraDecollo = oraDecollo;
		this.oraAtterraggio = oraAtterraggio;
		this.stato = stato;
		this.airbus = airbus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOraDecollo() {
		return oraDecollo;
	}

	public void setOraDecollo(LocalTime oraDecollo) {
		this.oraDecollo = oraDecollo;
	}

	public LocalTime getOraAtterraggio() {
		return oraAtterraggio;
	}

	public void setOraAtterraggio(LocalTime oraAtterraggio) {
		this.oraAtterraggio = oraAtterraggio;
	}

	public StatoTratta getStato() {
		return stato;
	}

	public void setStato(StatoTratta stato) {
		this.stato = stato;
	}

	public Airbus getAirbus() {
		return airbus;
	}

	public void setAirbus(Airbus airbus) {
		this.airbus = airbus;
	}

	public static TrattaDTO createTrattaDTOFromModel(Tratta tratta) {
		return new TrattaDTO(tratta.getId(), tratta.getCodice(), tratta.getDescrizione(), tratta.getData(),
				tratta.getOraDecollo(), tratta.getOraAtterraggio(), tratta.getStato(), tratta.getAirbus());
	}

	public static List<TrattaDTO> createTrattaDTOListFromModelList(List<Tratta> listInput) {

		return listInput.stream().map(trattaEntity -> {
			TrattaDTO result = TrattaDTO.createTrattaDTOFromModel(trattaEntity);
			return result;
		}).collect(Collectors.toList());
	}

	public Tratta buildTrattaModel() {
		
		return new Tratta(id, codice, descrizione, data, oraDecollo, oraAtterraggio, stato, airbus);
	}

}
