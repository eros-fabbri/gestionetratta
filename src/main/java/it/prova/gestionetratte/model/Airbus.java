package it.prova.gestionetratte.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "airbus")
public class Airbus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "codice")
	private String codice;
	
	@Column(name = "descrizione")
	private String descrizione;
	
	@Column(name = "datainizioservizio")
	private LocalDate dataInizioServizio;
	
	@Column(name = "numeropasseggeri")
	private Integer numeroPasseggeri;
	
	@JsonIgnoreProperties(value = { "airbus" })
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "airbus")
	private List<Tratta> tratte = new ArrayList<Tratta>(0);
	
	public Airbus(Long id, String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri,
			List<Tratta> tratte) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
		this.tratte = tratte;
	}

	public Airbus(String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri) {
		super();
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

	public List<Tratta> getTratte() {
		return tratte;
	}

	public void setTratte(List<Tratta> tratte) {
		this.tratte = tratte;
	}

	public Airbus() {
		
	}
	
	
}
