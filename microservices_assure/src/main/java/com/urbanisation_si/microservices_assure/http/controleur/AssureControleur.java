
package com.urbanisation_si.microservices_assure.http.controleur;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
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
import com.urbanisation_si.microservices_assure.configuration.ApplicationPropertiesConfiguration;
import com.urbanisation_si.microservices_assure.dao.AssureRepository;
import com.urbanisation_si.microservices_assure.exceptions.AssureIntrouvableException;
import com.urbanisation_si.microservices_assure.modele.Assure;

import io.swagger.annotations.Api;

@Api(description = "API pour les opérations CRUD pour les assurés")
@RestController // et pas @Controller sinon ne traduit pas les retours des objets Java en JSON
@RequestMapping(path = "/previt")

public class AssureControleur implements HealthIndicator {
	

	@Autowired
	private AssureRepository assureRepository;
	
	@Autowired
	ApplicationPropertiesConfiguration appProperties;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Health health() {
		// TODO Auto-generated method stub
		return Health.down().build();
	}
	@PostMapping(path = "/ajouterAssure")
	public ResponseEntity<Void> creerAssure(@Valid @RequestBody Assure assure) {
		
		log.info("---------> Appel creerAssure");
		
		Assure assureAjoute = assureRepository.save(assure);

		if (assureAjoute == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(assureAjoute.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

//	@GetMapping(path = "/listerLesAssures")
//	public @ResponseBody Iterable<Assure> getAllAssures() {
//		return assureRepository.findAll();
//	}
	
	@GetMapping(path="/listerLesAssures")
    public @ResponseBody List<Assure> getAllAssures() {

        Iterable<Assure> assuresIterable = assureRepository.findAll();
        
        List<Assure> assuresList = StreamSupport 
                .stream(assuresIterable.spliterator(), false) 
                .collect(Collectors.toList());
        
        if(assuresList.size()>appProperties.getLimiteNombreAssure()) {
        	
        	return assuresList.subList(0, appProperties.getLimiteNombreAssure());
        }
       
        return assuresList;
        
       
	}


// ------------------RECHERCHE D'ASSURERS PAR PARAM------------------

@GetMapping(path="/Assure/{id}")
	public Optional<Assure> rechercheAssure(@PathVariable Integer id){
		return assureRepository.findById(id);
}


	@GetMapping(path = "/Assure/numeroAssure/{numeroAssure}")
	public List<Assure> rechercherAssureNumeroAssure(@PathVariable Long numeroAssure) {
		return assureRepository.rechercherAssureNumeroAssure(numeroAssure);
	}
	
	@GetMapping(path="/Assure/numeroPersonne/{numeroPersonne}")
	public List<Assure> rechercheAssureParNumeroPersonne(@PathVariable Long numeroPersonne){
		return assureRepository.findByNumeroPersonne(numeroPersonne);
	
	}
	
	@GetMapping(path="/Assure/nomPrenom/{nom}/{prenom}")
	public List<Assure> rechercheAssureParNomPrenom(@PathVariable String nom, @PathVariable String prenom){
		return assureRepository.findByNomAndPrenom(nom, prenom);	
	}
//-------------EXERCICES DU 04/11---------------------

	@GetMapping(path = "/Assure/parChaine/{chaine}")
	public List<Assure> rechercheAssureParChaine(@PathVariable String chaine) {
		return assureRepository.findByNomContaining(chaine);
	}

	@GetMapping(path = "/Assure/dateMax/{year}/{month}/{dayOfMonth}")
	public List<Assure> rechercheAssureParDateMax(@PathVariable Integer year, @PathVariable Integer month,
			@PathVariable Integer dayOfMonth) {
		return assureRepository.findByDateNaissanceBefore(LocalDate.of(year, month, dayOfMonth));
	}

//-------------------FIN DES EXERCICES-----------------
	
	@DeleteMapping(path = "/Assure/{id}")
	public void supprimerAssurer(@PathVariable Integer id) {
		assureRepository.deleteById(id);
	}
	
	@PutMapping (path="/modifierAssure")    
    public void modifierAssure(@RequestBody Assure assure) {

      assureRepository.save(assure);
    }

//	---------------METHODES ET UTILISATION DU FILTRE JSON-------------
	
	@GetMapping(path = "/AssureFiltre/{id}")
	public MappingJacksonValue rechercherAssureId(@PathVariable Integer id) {
		Optional<Assure> assure = assureRepository.findById(id);

		if (!assure.isPresent())
			throw new AssureIntrouvableException(String.format("L'assure avec l'id %d n'existepas !", id));

		// Créer le filtre
		FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "dossierMedical");

		Assure a = assure.get();
		List<Assure> ar = new ArrayList<Assure>();
		ar.add(a);

		return filtrerAssures(ar, listeFiltres);

	}

	/**
	 * Création d'un filtre dynamique
	 */
	public FilterProvider creerFiltre(String nomFiltre, String attribut) {
		SimpleBeanPropertyFilter unFiltre;
		if (attribut == null) {
			unFiltre = SimpleBeanPropertyFilter.serializeAll();
		} else {
			unFiltre = SimpleBeanPropertyFilter.serializeAllExcept(attribut);
		}
		return new SimpleFilterProvider().addFilter(nomFiltre, unFiltre);
	}

	/**
	 * Renvoi de la liste filtrée
	 * 
	 * @param assures
	 * @param listeFiltres
	 * @return MappingJacksonValue
	 */
	public MappingJacksonValue filtrerAssures(List<Assure> assures, FilterProvider listeFiltres) {
		MappingJacksonValue assuresFiltres = new MappingJacksonValue(assures);
		assuresFiltres.setFilters(listeFiltres);
		return assuresFiltres;
	}

	
	
}
