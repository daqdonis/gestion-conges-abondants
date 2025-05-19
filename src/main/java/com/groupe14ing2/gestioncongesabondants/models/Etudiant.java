package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;
import java.sql.Date;

public class Etudiant implements Serializable {
  private long idEtu;
  private String nom;
  private String prenom;
  private Date dateNaiss;
  private String idGroupe;

  public Etudiant(long idEtu, String nom, String prenom, Date dateNaiss, String idGroupe) {
    this.idEtu = idEtu;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.idGroupe = idGroupe;
  }

  public Etudiant(String nom, String prenom, Date dateNaiss, String idGroupe) {
    this(0, nom, prenom, dateNaiss, idGroupe);
  }

  // Getters and setters
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

  public Date getDateNaiss() {
    return dateNaiss;
  }

  public void setDateNaiss(Date dateNaiss) {
    this.dateNaiss = dateNaiss;
  }

  public String getIdGroupe() {
    return idGroupe;
  }

  public void setIdGroupe(String idGroupe) {
    this.idGroupe = idGroupe;
  }
}