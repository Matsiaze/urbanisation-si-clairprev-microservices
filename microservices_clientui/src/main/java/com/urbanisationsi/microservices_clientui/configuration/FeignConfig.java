package com.urbanisationsi.microservices_clientui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfig {
@Bean
	public BasicAuthRequestInterceptor mBasicAuthRequestInterceptor(){
		return  new BasicAuthRequestInterceptor("utilisateur", "mdp");
	}
}
