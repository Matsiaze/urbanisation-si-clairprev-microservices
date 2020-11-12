package com.urbanisationsi.microservices_clientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.urbanisationsi.microservices_clientui.beans.ContratBean;

@FeignClient(name="zuul-server", decode404 = true
//, url="localhost:9997"
)
@RibbonClient(name="microservice-contrat")
public interface MicroserviceContratProxy {
	
	@GetMapping(path="microservice-contrat/previt/contrat/na/{numeroAssure}")
	public List<ContratBean> rechercherContratParAssure(@PathVariable Long numeroAssure);
	
	@GetMapping(path="microservice-contrat/previt/contrat/nc/{numeroContrat}")
	public List<ContratBean> rechercherContratParNumero(@PathVariable Long numeroContrat);
	
	@GetMapping(path="microservice-contrat/previt/contrat/{numeroAssure}/{numeroProduit}")
	public ContratBean affecterNumeroProduit(@PathVariable Long numeroAssure, @PathVariable Long numeroProduit);
	
	@GetMapping(path="microservice-contrat/previt/contrat/finaliserContrat/{numeroAssure}/{numeroContrat}/{dateDebut}")
	public ContratBean finaliserContrat(@PathVariable Long numeroAssure, @PathVariable Long numeroContrat, @PathVariable  String dateDebut);
//			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut);

}
