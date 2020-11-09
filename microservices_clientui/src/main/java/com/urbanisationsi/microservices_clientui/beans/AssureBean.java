package com.urbanisationsi.microservices_clientui.beans;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AssureBean {

	private String nom;
	
	private String prenom;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateNaissance;
	
	private Long numeroAssure;
	
	private String dossierMedical;

	
	public AssureBean() {
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Long getNumeroAssure() {
		return numeroAssure;
	}

	public void setNumeroAssure(Long numeroAssure) {
		this.numeroAssure = numeroAssure;
	}

	public String getDossierMedical() {
		return dossierMedical;
	}

	public void setDossierMedical(String dossierMedical) {
		this.dossierMedical = dossierMedical;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssureBean [nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", dateNaissance=");
		builder.append(dateNaissance);
		builder.append(", numeroAssure=");
		builder.append(numeroAssure);
		builder.append(", dossierMedical=");
		builder.append(dossierMedical);
		builder.append("]");
		return builder.toString();
	}

	
	
}
