package com.henteti.feres.webservicevolley.Models;

import java.io.Serializable;

public class Medecin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1256609551175042164L;
	private Long idMedecin;
	private String nom;
	private String prenom;
	private String grade;
	private String specialite;
	private boolean actif;
	
	
	public Medecin() {
		super();
	}


	public Medecin(Long idMedecin, String nom, String prenom, String grade,
			String specialite) {
		super();
		this.idMedecin = idMedecin;
		this.nom = nom;
		this.prenom = prenom;
		this.grade = grade;
		this.specialite = specialite;
	}


	public Long getIdMedecin() {
		return idMedecin;
	}


	public void setIdMedecin(Long idMedecin) {
		this.idMedecin = idMedecin;
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


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getSpecialite() {
		return specialite;
	}


	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}


	public boolean isActif() {
		return actif;
	}


	public void setActif(boolean actif) {
		this.actif = actif;
	}
}
