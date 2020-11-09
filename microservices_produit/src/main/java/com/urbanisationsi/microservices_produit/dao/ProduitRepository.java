package com.urbanisationsi.microservices_produit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.urbanisationsi.microservices_produit.modele.Produit;

public interface ProduitRepository extends CrudRepository<Produit, Integer>{

	List<Produit> findByNumeroProduit(Long numeroProduit);
	
	List<Produit> findByLibelleContaining(String chaine);
	
}
