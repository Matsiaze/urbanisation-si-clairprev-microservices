/**
 * 
 */
package com.urbanisation_si.microservices_assure.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.urbanisation_si.microservices_assure.modele.Assure;

/**
 * @author Patrice & Claudia
 *
 */
public interface AssureRepository extends CrudRepository<Assure, Integer> {

	/**
	 * JPQL
	 */
	@Query("from Assure a where a.numeroAssure = :na ")
	List<Assure> rechercherAssureNumeroAssure(@Param("na") Long numeroAssure);

	List<Assure> findByNomContaining(String chaine);

	List<Assure> findByDateNaissanceBefore(LocalDate date);

	List<Assure> findByNomAndPrenom(String nom, String prenom);

	List<Assure> findByNumeroPersonne(Long numeroPersonne);

}
