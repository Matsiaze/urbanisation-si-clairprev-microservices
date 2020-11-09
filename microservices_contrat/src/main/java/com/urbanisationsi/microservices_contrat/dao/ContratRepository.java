package com.urbanisationsi.microservices_contrat.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.urbanisationsi.microservices_contrat.modele.Contrat;

public interface ContratRepository extends CrudRepository<Contrat, Integer>{

	List<Contrat> findByNumeroContrat(Long numeroContrat);
	List<Contrat> findByNumeroAssure(Long numeroAssure);
	List<Contrat> findByNumeroProduit(Long numeroProduit);
	
}
