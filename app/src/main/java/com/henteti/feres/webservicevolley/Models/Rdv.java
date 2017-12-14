package com.henteti.feres.webservicevolley.Models;

import java.io.Serializable;

public class Rdv implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7746051823913631148L;
	
	private Long idRDV;
	private String dateRDV;
	private String trancheHeure;
	private Patient patient;
	
	public Rdv() {
		super();
	}

	public Rdv(Long idRDV, String dateRDV, String trancheHeure, Patient patient) {
		super();
		this.idRDV = idRDV;
		this.dateRDV = dateRDV;
		this.trancheHeure = trancheHeure;
		this.patient = patient;
	}
	public Long getIdRDV() {
		return idRDV;
	}
	public void setIdRDV(Long idRDV) {
		this.idRDV = idRDV;
	}
	public String getDateRDV() {
		return dateRDV;
	}
	public void setDateRDV(String dateRDV) {
		this.dateRDV = dateRDV;
	}
	public String getTrancheHeure() {
		return trancheHeure;
	}
	public void setTrancheHeure(String trancheHeure) {
		this.trancheHeure = trancheHeure;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
