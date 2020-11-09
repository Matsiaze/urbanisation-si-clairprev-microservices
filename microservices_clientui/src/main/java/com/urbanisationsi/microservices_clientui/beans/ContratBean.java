package com.urbanisationsi.microservices_clientui.beans;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContratBean {

	
	
	private String dateDebut;
	
	private Long numeroContrat;
	
	private Long numeroAssure;
	
	private Long numeroProduit;
	
	
	public ContratBean() {
		// TODO Auto-generated constructor stub
	}

	

	public String getDateDebut() {
		return dateDebut;
	}



	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Long getNumeroContrat() {
		return numeroContrat;
	}

	public void setNumeroContrat(Long numeroContrat) {
		this.numeroContrat = numeroContrat;
	}

	public Long getNumeroAssure() {
		return numeroAssure;
	}

	public void setNumeroAssure(Long numeroAssure) {
		this.numeroAssure = numeroAssure;
	}

	public Long getNumeroProduit() {
		return numeroProduit;
	}

	public void setNumeroProduit(Long numeroProduit) {
		this.numeroProduit = numeroProduit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContratBean [dateDebut=");
		builder.append(dateDebut);
		builder.append(", numeroContrat=");
		builder.append(numeroContrat);
		builder.append(", numeroAssure=");
		builder.append(numeroAssure);
		builder.append(", numeroProduit=");
		builder.append(numeroProduit);
		builder.append("]");
		return builder.toString();
	}
	
	
}
