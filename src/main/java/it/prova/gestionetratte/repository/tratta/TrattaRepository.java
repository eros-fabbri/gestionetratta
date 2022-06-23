package it.prova.gestionetratte.repository.tratta;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Tratta;

public interface TrattaRepository extends CrudRepository<Tratta, Long>, CustomTrattaRepository {
	@Query("from Tratta t join fetch t.airbus where t.id = ?1")
	Tratta findSingleTrattaEager(Long id);

	@Modifying
	@Query("update Tratta t set t.stato='CONCLUSA' where t.oraAtterraggio<=:oraAtterraggio and t.stato='ATTIVA'")
	Object concludiTratte(LocalTime oraAtterraggio);
}
