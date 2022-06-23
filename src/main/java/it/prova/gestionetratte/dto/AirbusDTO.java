package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;

public class AirbusDTO {

	private Long id;
	@NotBlank
	private String codice;
	@NotBlank
	private String descrizione;
	@NotNull
	private LocalDate dataInizioServizio;
	@NotNull
	private Integer numeroPasseggeri;
	private List<TrattaDTO> tratte = new ArrayList<TrattaDTO>();

	public AirbusDTO(Long id, @NotBlank String codice, @NotBlank String descrizione,
			@NotNull LocalDate dataInizioServizio, @NotNull Integer numeroPasseggeri) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
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

	public LocalDate getDataInizioServizio() {
		return dataInizioServizio;
	}

	public void setDataInizioServizio(LocalDate dataInizioServizio) {
		this.dataInizioServizio = dataInizioServizio;
	}

	public Integer getNumeroPasseggeri() {
		return numeroPasseggeri;
	}

	public void setNumeroPasseggeri(Integer numeroPasseggeri) {
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public List<TrattaDTO> getTratte() {
		return tratte;
	}

	public void setTratte(List<TrattaDTO> tratte) {
		this.tratte = tratte;
	}
	
	public Airbus buildAirbusModel() {
		return null;
	}

	public static AirbusDTO buildAirbusDTOFromModel(Airbus airbusInput, boolean includiTratte) {
		AirbusDTO result = new AirbusDTO(airbusInput.getId(), airbusInput.getCodice(), airbusInput.getDescrizione(),
				airbusInput.getDataInizioServizio(), airbusInput.getNumeroPasseggeri());

		if (includiTratte)
			result.setTratte(TrattaDTO.createTrattaDTOListFromModelList(airbusInput.getTratte()));
		return result;

	}

	public static List<AirbusDTO> createAirbusDTOListFromModelList(List<Airbus> airbusList, boolean includiTratte){
		return airbusList.stream().map(airbusEntity -> {
			AirbusDTO result = AirbusDTO.buildAirbusDTOFromModel(airbusEntity,includiTratte);
			if(includiTratte)
				result.setTratte(TrattaDTO.createTrattaDTOListFromModelList(airbusEntity.getTratte()));
			return result;
		}).collect(Collectors.toList());
	}
	

}
