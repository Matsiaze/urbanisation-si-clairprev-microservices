package com.urbanisationsi.microservices_clientui.controleur;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.urbanisationsi.microservices_clientui.beans.AssureBean;
import com.urbanisationsi.microservices_clientui.beans.ContratBean;
import com.urbanisationsi.microservices_clientui.beans.ProduitBean;
import com.urbanisationsi.microservices_clientui.proxies.MicroserviceAssureProxy;
import com.urbanisationsi.microservices_clientui.proxies.MicroserviceContratProxy;
import com.urbanisationsi.microservices_clientui.proxies.MicroserviceProduitProxy;

@Controller
public class ClientControleur {

	@Autowired
	private MicroserviceAssureProxy microserviceAssureProxy;

	@Autowired
	private MicroserviceContratProxy microserviceContratProxy;
	
	@Autowired
	private MicroserviceProduitProxy microserviceProduitProxy;
	
	private Long numeroAssure;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/")
	public String accueil(Model model) {
		log.info("*********** Appel accueil");

		List<AssureBean> assures = microserviceAssureProxy.getAllAssures();

		log.info("*********** LISTE DES ASSURES *********" + assures);

		model.addAttribute("assureBean", new AssureBean());
		model.addAttribute("assures", assures);
		return "Accueil";
	}

	
	
	@PostMapping(value = "/saisir-assure")
	public String saisirAssure(AssureBean assureBean, Model model) {

		List<AssureBean> assuresTrouves = microserviceAssureProxy.rechercheAssureParNomPrenom(assureBean.getNom(),
				assureBean.getPrenom());

		List<AssureBean> assures = microserviceAssureProxy.getAllAssures();

		if (assuresTrouves.isEmpty()) {
			assures.add(assureBean);
		}

		model.addAttribute("assures", assuresTrouves);
		
		return "ListeAssure";
	}

	
	@GetMapping(value = "/creerContrat/{numeroAssure}")
	public String creerContrat(ProduitBean produitBean, @PathVariable Long numeroAssure, Model model) {
		
//		List<ContratBean> contratsTrouves = microserviceContratProxy
//				.rechercherContratParAssure(contratBean.getNumeroAssure());
		List<AssureBean> assures = microserviceAssureProxy.findbyNumeroAssure(numeroAssure);
		
		AssureBean assureSelect = assures.get(0);
		
		List<ProduitBean> produits = microserviceProduitProxy
				.getAllProduits();
		
		model.addAttribute("produits", produits);
		model.addAttribute("assureSelect", assureSelect);
	
		
		return "ListeProduitsAssure";

	}
	@GetMapping(value="/affecterNumeroProduit/{numeroAssure}/{numeroProduit}")
	public String affecterNumeroProduit(@PathVariable Long numeroAssure, @PathVariable Long numeroProduit, Model model) {
		
		ContratBean contratSelect = microserviceContratProxy.affecterNumeroProduit(numeroAssure, numeroProduit);
		
		//contratSelect.setNumeroProduit(numeroProduit);
		model.addAttribute("contratSelect", contratSelect);
		
		return "FinaliserContrat";
		
	}
	
	@GetMapping(value="/finaliserContrat/{numeroAssure}")
		public String finaliserContrat(@PathVariable Long numeroAssure, ContratBean contratSelect, Model model) {
		
		ContratBean contratCree = microserviceContratProxy.finaliserContrat(numeroAssure, contratSelect.getNumeroContrat(), contratSelect.getDateDebut());
		
		model.addAttribute("contratCree", contratCree);
		
		return "Confirmation";
	}

	public Long getNumeroAssure() {
		return numeroAssure;
	}

	public void setNumeroAssure(Long numeroAssure) {
		this.numeroAssure = numeroAssure;
	}

	
	
}
