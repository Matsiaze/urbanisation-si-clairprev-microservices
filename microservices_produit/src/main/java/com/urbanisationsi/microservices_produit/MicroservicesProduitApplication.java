package com.urbanisationsi.microservices_produit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class MicroservicesProduitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesProduitApplication.class, args);
	}

}
