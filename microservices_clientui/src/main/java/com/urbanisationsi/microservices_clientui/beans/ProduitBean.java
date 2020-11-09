package com.urbanisationsi.microservices_clientui.beans;

public class ProduitBean {

	private Long numeroProduit;

	private String libelle;

	
	public ProduitBean() {
		// TODO Auto-generated constructor stub
	}

	public Long getNumeroProduit() {
		return numeroProduit;
	}

	public void setNumeroProduit(Long numeroProduit) {
		this.numeroProduit = numeroProduit;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProduitBean [numeroProduit=");
		builder.append(numeroProduit);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}

	
	
}
