package com.urbanisationsi.microservices_clientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.urbanisationsi.microservices_clientui.beans.AssureBean;


@FeignClient(name="microservice-assure", url="localhost:9999")
public interface MicroserviceAssureProxy  {
	
	@GetMapping(path="/previt/listerLesAssures")
	public List<AssureBean> getAllAssures() ;
	
	@GetMapping(path="/previt/Assure/nomPrenom/{nom}/{prenom}")
	public List<AssureBean> rechercheAssureParNomPrenom(@PathVariable String nom, @PathVariable String prenom);
	
	@GetMapping(path="/previt/Assure/numeroAssure/{numeroAssure}")
	public List<AssureBean> findbyNumeroAssure(@PathVariable Long numeroAssure);

	
}
