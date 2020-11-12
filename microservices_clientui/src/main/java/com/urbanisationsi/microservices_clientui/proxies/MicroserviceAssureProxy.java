package com.urbanisationsi.microservices_clientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.urbanisationsi.microservices_clientui.beans.AssureBean;


@FeignClient(name="zuul-server", decode404 = true
//, url="localhost:9999"
)
@RibbonClient(name="microservice-assure")
public interface MicroserviceAssureProxy  {
	
	@GetMapping(path="microservice-assure/previt/listerLesAssures")
	public List<AssureBean> getAllAssures() ;
	
	@GetMapping(path="microservice-assure/previt/Assure/nomPrenom/{nom}/{prenom}")
	public List<AssureBean> rechercheAssureParNomPrenom(@PathVariable String nom, @PathVariable String prenom);
	
	@GetMapping(path="microservice-assure/previt/Assure/numeroAssure/{numeroAssure}")
	public List<AssureBean> findbyNumeroAssure(@PathVariable Long numeroAssure);

	
}
