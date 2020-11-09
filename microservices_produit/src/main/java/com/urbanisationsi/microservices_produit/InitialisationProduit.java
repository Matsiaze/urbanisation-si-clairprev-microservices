package com.urbanisationsi.microservices_produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.urbanisationsi.microservices_produit.dao.ProduitRepository;
import com.urbanisationsi.microservices_produit.modele.Produit;

@Component
public class InitialisationProduit implements ApplicationRunner{

	@Autowired
	private ProduitRepository produitRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Produit p1 = new Produit();
		p1.setLibelle("Prevoyance");
		p1.setNumeroProduit(194L);
		produitRepository.save(p1);
		
		Produit p2 = new Produit();
		p2.setLibelle("AssurVie");
		p2.setNumeroProduit(150L);
		produitRepository.save(p2);
		
		Produit p3 = new Produit();
		p3.setLibelle("Prevoyance Famille");
		p3.setNumeroProduit(198L);
		produitRepository.save(p3);
		
		Produit p4 = new Produit();
		p4.setLibelle("AssurVie Famille");
		p4.setNumeroProduit(155L);
		produitRepository.save(p4);
		
		Produit p5 = new Produit();
		p5.setLibelle("Prevoyance Individuelle");
		p5.setNumeroProduit(190L);
		produitRepository.save(p5);
		
		Produit p6 = new Produit();
		p6.setLibelle("AssurVie Individuelle");
		p6.setNumeroProduit(156L);
		produitRepository.save(p6);
	}

}
