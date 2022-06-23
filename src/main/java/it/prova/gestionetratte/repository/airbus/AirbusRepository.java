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

	@Query(value = "select distinct a.* from Tratta t1, Tratta t2, Airbus a where t1.airbus_id = a.id and t1.airbus_id = t2.airbus_id and t1.id <> t2.id and ((t1.oraatterraggio between t2.oradecollo and t2.oraatterraggio) or (t1.oradecollo between t2.oradecollo and t2.oraatterraggio))", nativeQuery = true)
	List<Airbus> findAllConSovrapposizione();

	@Query(value = "select distinct a.* from Tratta t1, Tratta t2, Airbus a where t1.airbus_id = a.id and t1.airbus_id = t2.airbus_id and t1.id <> t2.id and ((t1.oraatterraggio not between t2.oradecollo and t2.oraatterraggio) and (t1.oradecollo not between t2.oradecollo and t2.oraatterraggio))", nativeQuery = true)
	List<Airbus> findAllSenzaSovrapposizione();
}
