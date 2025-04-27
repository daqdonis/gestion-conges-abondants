package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;
import java.sql.Date;

public class Etudiant implements Serializable {
  private long idEtu;
  private String nom;
  private String prenom;
  private java.sql.Date dateNaiss;
  private long idGroupe;
  // REMOVED: idConge and idDemReins fields

  public Etudiant(long idEtu, String nom, String prenom, Date dateNaiss, long idGroupe) {
    this.idEtu = idEtu;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.idGroupe = idGroupe;
  }

  public Etudiant(String nom, String prenom, Date dateNaiss, long idGroupe) {
    this(0, nom, prenom, dateNaiss, idGroupe);
  }

  // REMOVE all getIdConge(), setIdConge(), getIdDemReins(), setIdDemReins() methods

  // Keep all other getters and setters except those removed above
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
}