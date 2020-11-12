package com.urbanisationsi.microservices_clientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.urbanisationsi.microservices_clientui.beans.ProduitBean;

@FeignClient(name = "zuul-server"
//, url = "localhost:9998"
)
@RibbonClient(name="microservice-produit")
public interface MicroserviceProduitProxy {

	@GetMapping(path = "microservice-produit/previt/produit/numero/{numeroProduit}")
	public List<ProduitBean> rechercherProduitParNumero(@PathVariable Long numeroProduit);
	
	@GetMapping(path = "microservice-produit/previt/listerLesProduits")
	public List<ProduitBean> getAllProduits();
}
