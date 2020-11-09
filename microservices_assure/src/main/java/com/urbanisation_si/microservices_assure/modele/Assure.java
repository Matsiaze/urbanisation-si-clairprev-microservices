/**
 * 
 */
package com.urbanisation_si.microservices_assure.modele;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * @author Patrice
 *
 */
@Entity
//@JsonIgnoreProperties(value = {"dossierMedical", "numeroPersonne"})
//@JsonFilter("filtreDynamiqueJson")
public class Assure extends Personne {

	public Assure() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long numeroAssure;
	
//	@JsonIgnore
	private String dossierMedical;

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

	public Assure(Integer id, String nom, String prenom, Long numeroPersonne, LocalDate dateNaissance, Long numeroAssure,
			String dossierMedical) {
		super(id, nom, prenom, numeroPersonne, dateNaissance);
		this.numeroAssure = numeroAssure;
		this.dossierMedical = dossierMedical;
	}

	public Assure(Integer id, String nom, String prenom, Long numeroPersonne, LocalDate dateNaissance) {
		super(id, nom, prenom, numeroPersonne, dateNaissance);
		// TODO Auto-generated constructor stub
	}

	
	
	

}
