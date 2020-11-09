package com.urbanisationsi.microservices_produit.http.controleur;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.urbanisationsi.microservices_produit.configuration.ApplicationPropertiesConfiguration;
import com.urbanisationsi.microservices_produit.dao.ProduitRepository;
import com.urbanisationsi.microservices_produit.exceptions.ProduitIntrouvableException;
import com.urbanisationsi.microservices_produit.modele.Produit;

import io.swagger.annotations.Api;

@Api(description = "API pour les opérations CRUD pour les produits")
@RestController
@RequestMapping(path = "/previt")

public class ProduitControleur {

	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	ApplicationPropertiesConfiguration appProperties;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path = "/ajouterProduit")
	public ResponseEntity<Void> creerProduit(@RequestBody Produit produit) {
		log.info("---------> Appel creerProduit");
		Produit produitAjoute = produitRepository.save(produit);

		if (produitAjoute == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(produitAjoute.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
//-------------------------QUELQUES REQUETES ---------------------------------------
//	@GetMapping(path = "/listerLesProduits")
//	public @ResponseBody Iterable<Produit> getAllProduits() {
//		return produitRepository.findAll();
//	}
	
	@GetMapping(path="/listerLesProduits")
		public @ResponseBody List<Produit> getAllProduits(){
			
			Iterable<Produit> produitsIterable = produitRepository.findAll();
			
			List<Produit> produitsList= StreamSupport
					.stream(produitsIterable.spliterator(), false)
					.collect(Collectors.toList());
			
			if(produitsList.size()>appProperties.getLimiteNombreProduit()) {
				return produitsList.subList(0, appProperties.getLimiteNombreProduit());
			}
		return produitsList;
	}
	
	@GetMapping(path="/produit/{id}")
	public @ResponseBody Optional<Produit> rechercheProduitParId(@PathVariable Integer id){
		 Optional<Produit> produit = produitRepository.findById(id);
		 
		 if(!produit.isPresent()) throw new ProduitIntrouvableException(String.format("Aucun produit trouvé avec l'id %d", id));
		
		return produitRepository.findById(id);
	}
	
	@GetMapping(path="/produit/numero/{numeroProduit}")
	public @ResponseBody Iterable<Produit> rechercheProduitParNumero(@PathVariable Long numeroProduit){
		
		List<Produit> result= produitRepository.findByNumeroProduit(numeroProduit);

		if (result.size()==0) throw new ProduitIntrouvableException(String.format("Aucun produit ne possede le numero %d", numeroProduit));
		return result;
	}
	
	@GetMapping(path="/Produit/{libelle}")
	public @ResponseBody Iterable<Produit> rechercheProduitParLibelle(@PathVariable String chaine){
		return produitRepository.findByLibelleContaining(chaine);
	}
	
	@DeleteMapping(path = "/Produit/{id}")
	public void supprimerProduit(@PathVariable Integer id) {
		produitRepository.deleteById(id);
	}
	
	@PutMapping (path="/modifierProduit")    
    public void modifierProduit(@RequestBody Produit produit) {
      produitRepository.save(produit);
    }
	
	
	
//---------------------------------TEST AVEC FILTRE JSON SUR NUMERO PRODUIT-------------------------------
	
	@GetMapping(path = "/produitFiltre/{id}")
	public MappingJacksonValue rechercherProduitParIdFiltre(@PathVariable Integer id) {
		Optional<Produit> produit = produitRepository.findById(id);

		if (!produit.isPresent())
			throw new ProduitIntrouvableException(String.format("Le produit avec l'id %d n'existepas!", id));

//Création du filtre
		FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "numeroProduit");

		Produit p = produit.get();
		List<Produit> produits = new ArrayList<>();
		produits.add(p);

		return filtrerProduits(produits, listeFiltres);

	}

//Création du filtre dyn
	public FilterProvider creerFiltre(String nomFiltre, String attribut) {
		SimpleBeanPropertyFilter unFiltre;
		if (attribut == null) {
			unFiltre = SimpleBeanPropertyFilter.serializeAll();
		} else {
			unFiltre = SimpleBeanPropertyFilter.serializeAllExcept(attribut);
		}
		return new SimpleFilterProvider().addFilter(nomFiltre, unFiltre);
	}

//Récupération de la liste filtrée
	public MappingJacksonValue filtrerProduits(List<Produit> produits, FilterProvider listeDeFiltres) {
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeFiltres);
		return produitsFiltres;
	}
}

