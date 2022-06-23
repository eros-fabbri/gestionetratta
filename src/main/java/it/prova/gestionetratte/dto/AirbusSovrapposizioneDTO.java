package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.gestionetratte.model.Airbus;

public class AirbusSovrapposizioneDTO extends AirbusDTO{

	protected Long id;
	protected String codice;
	protected String descrizione;
	protected LocalDate dataInizioServizio;
	protected Integer numeroPasseggeri;
	private boolean conSovrapposizione;
	
	
	public AirbusSovrapposizioneDTO(Long id, @NotBlank String codice, @NotBlank String descrizione,
			@NotNull LocalDate dataInizioServizio, @NotNull Integer numeroPasseggeri) {
		super(id, codice, descrizione, dataInizioServizio, numeroPasseggeri);
		
		
	}
	
	public boolean isConSovrapposizione() {
		return conSovrapposizione;
	}



	public void setConSovrapposizione(boolean conSovrapposizione) {
		this.conSovrapposizione = conSovrapposizione;
	}



	public static AirbusSovrapposizioneDTO buildAirbusDTOFromModel(Airbus airbusInput, boolean includiTratte) {
		AirbusSovrapposizioneDTO result = new AirbusSovrapposizioneDTO(airbusInput.getId(), airbusInput.getCodice(), airbusInput.getDescrizione(),
				airbusInput.getDataInizioServizio(), airbusInput.getNumeroPasseggeri());

		if (includiTratte)
			result.setTratte(TrattaDTO.createTrattaDTOListFromModelList(airbusInput.getTratte()));
		return result;
	}
	
	public static AirbusSovrapposizioneDTO builAirbusSovrappostoDTOFromAirbus(Airbus airbus, boolean sovrapposto) {
		
		AirbusSovrapposizioneDTO result = AirbusSovrapposizioneDTO.buildAirbusDTOFromModel(airbus, false);
		
		if(sovrapposto)
			result.setConSovrapposizione(true);
		return result;
		
		
	}
	
	public static List<AirbusSovrapposizioneDTO> buildAirbusSovrappostoListFromAirbusList(List<Airbus> listInput, boolean sovrapposto){
		return listInput.stream().map(airbusEntity -> {
			AirbusSovrapposizioneDTO result = AirbusSovrapposizioneDTO.builAirbusSovrappostoDTOFromAirbus(airbusEntity,sovrapposto);
			if(sovrapposto) {
				result.setConSovrapposizione(true);
			}
			return result;
		}).collect(Collectors.toList());
		
	}

}
