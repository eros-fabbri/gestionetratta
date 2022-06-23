package it.prova.gestionetratte.repository.airbus;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;


public interface AirbusRepository extends CrudRepository<Airbus, Long>, CustomAirbusRepository {
	
	@Query("from Airbus a join fetch a.tratte where a.id = ?1")
	Airbus findSingleAirbusEager(Long id);
	
	@Query("from Airbus a join fetch a.tratte where a.id = a.id")
	List<Airbus> findAllEager();
}
