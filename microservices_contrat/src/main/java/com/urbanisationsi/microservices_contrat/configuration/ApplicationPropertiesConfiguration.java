package com.urbanisationsi.microservices_contrat.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("urbanisation-si.clairprev")
@RefreshScope
public class ApplicationPropertiesConfiguration {

	private int limiteNombreContrat;

	public int getLimiteNombreContrat() {
		return limiteNombreContrat;
	}

	public void setLimiteNombreContrat(int limiteNombreContrat) {
		this.limiteNombreContrat = limiteNombreContrat;
	}
	
	
}
