package com.urbanisationsi.microservices_contrat.http.controleur;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.urbanisationsi.microservices_contrat.configuration.ApplicationPropertiesConfiguration;
import com.urbanisationsi.microservices_contrat.dao.ContratRepository;
import com.urbanisationsi.microservices_contrat.exceptions.ContratIntrouvableException;
import com.urbanisationsi.microservices_contrat.modele.Contrat;

import io.swagger.annotations.Api;

@RequestMapping(path = "/previt")
@RestController
@Api(description = "API pour les opérations CRUD pour les contrats")

public class ContratControleur {

	@Autowired
	private ContratRepository contratRepository;
	
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	
	@PostMapping(path = "/ajouterContrat")
	public ResponseEntity<Void> creerContrat(@RequestBody Contrat contrat) {
		
		
		Contrat contratAjoute = contratRepository.save(contrat);

		if (contratAjoute == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(contratAjoute.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
//	@GetMapping(path = "/listerLesContrats")
//	//public MappingJacksonValue getAllContrats() {
//	public @ResponseBody Iterable<Contrat> getAllContrats() {
//		return contratRepository.findAll();
//	}
	
	
	@GetMapping(path="/listerLesContrats")
	public @ResponseBody List<Contrat> getAllContrats(){
		
		Iterable<Contrat> contratsList = contratRepository.findAll();
		
		List<Contrat> contratsIterable = StreamSupport
				.stream(contratsList.spliterator(), false)
				.collect(Collectors.toList());
		
		if(contratsIterable.size()>appProperties.getLimiteNombreContrat()) {
			return contratsIterable.subList(0, appProperties.getLimiteNombreContrat());
		}
		return contratsIterable;
	}
	
	@GetMapping(path="/contrat/{id}")
	public @ResponseBody Optional<Contrat> rechercheContratParId(@PathVariable Integer id){
		 Optional<Contrat> contrat = contratRepository.findById(id);
		 
		 if(!contrat.isPresent()) throw new ContratIntrouvableException(String.format("Aucun contrat trouvé avec l'id %d", id));
		
		return contratRepository.findById(id);
	}
	
	@GetMapping(path="/contrat/nc/{numeroContrat}")
	public @ResponseBody Iterable<Contrat> rechercheContratParNumero(@PathVariable Long numeroContrat){
		
		List<Contrat> result= contratRepository.findByNumeroContrat(numeroContrat);

		if (result.size()==0) throw new ContratIntrouvableException(String.format("Aucun contrat ne possede le numero de contrat:  %d", numeroContrat));
		return result;
	}
	
	@GetMapping(path="/contrat/na/{numeroAssure}")
	public @ResponseBody Iterable<Contrat> rechercheContratParNumeroAssure(@PathVariable Long numeroAssure){
		
		List<Contrat> result= contratRepository.findByNumeroAssure(numeroAssure);
		
		if (result.size()==0) throw new ContratIntrouvableException(String.format("Aucun contrat ne possede le numero assure:  %d", numeroAssure));
		return result;
	}
	
	@GetMapping(path="/contrat/np/{numeroProduit}")
	public @ResponseBody Iterable<Contrat> rechercheContratParNumeroProduit(@PathVariable Long numeroProduit){
		
		List<Contrat> result= contratRepository.findByNumeroProduit(numeroProduit);
		
		if (result.size()==0) throw new ContratIntrouvableException(String.format("Aucun contrat ne possede le numero de produit: %d", numeroProduit));
		return result;
	}
	
	@DeleteMapping(path = "/Contrat/{id}")
	public void supprimerContrat(@PathVariable Integer id) {
		contratRepository.deleteById(id);
	}
	
	@PutMapping (path="/modifierContrat")    
    public void modifierProduit(@RequestBody Contrat contrat) {
      contratRepository.save(contrat);
    }
	
	//---------------------------------TEST AVEC FILTRE JSON SUR NUMERO ASSURE-------------------------------
	
		@GetMapping(path = "/contratFiltre/{id}")
		public MappingJacksonValue rechercherContratParIdFiltre(@PathVariable Integer id) {
			Optional<Contrat> contrat = contratRepository.findById(id);

			if (!contrat.isPresent())
				throw new ContratIntrouvableException(String.format("Le contrat avec l'id %d n'existe pas!", id));

	//Création du filtre
			FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "numeroAssure");

			Contrat c = contrat.get();
			List<Contrat> contrats = new ArrayList<>();
			contrats.add(c);

			return filtrerContrats(contrats, listeFiltres);

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
		public MappingJacksonValue filtrerContrats(List<Contrat> contrats, FilterProvider listeDeFiltres) {
			MappingJacksonValue contratsFiltres = new MappingJacksonValue(contrats);
			contratsFiltres.setFilters(listeDeFiltres);
			return contratsFiltres;
		}
	
}
