package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;
import java.sql.Date;

public class Etudiant implements Serializable {

  private long idEtu;
  private String nom;
  private String prenom;
  private java.sql.Date dateNaiss;
  private long idGroupe;
  private Long idConge;
  private Long idDemReins;

  public Etudiant(long idEtu, String nom, String prenom, Date dateNaiss, long idGroupe, long idConge, long idDemReins) {
    this.idEtu = idEtu;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.idGroupe = idGroupe;
    this.idConge = idConge;
    this.idDemReins = idDemReins;
  }

  public Etudiant(String nom, String prenom, Date dateNaiss, long idGroupe) {
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.idGroupe = idGroupe;
    this.idConge = null;
    this.idDemReins = null;
  }

  public long getIdEtu() {
    return idEtu;
  }

  public void setIdEtu(long idEtu) {
    this.idEtu = idEtu;
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


  public java.sql.Date getDateNaiss() {
    return dateNaiss;
  }

  public void setDateNaiss(java.sql.Date dateNaiss) {
    this.dateNaiss = dateNaiss;
  }


  public long getIdGroupe() {
    return idGroupe;
  }

  public void setIdGroupe(long idGroupe) {
    this.idGroupe = idGroupe;
  }


  public long getIdConge() {
    return idConge;
  }

  public void setIdConge(long idConge) {
    this.idConge = idConge;
  }


  public long getIdDemReins() {
    return idDemReins;
  }

  public void setIdDemReins(long idDemReins) {
    this.idDemReins = idDemReins;
  }

}
