package com.groupe14ing2.gestioncongesabondants.models;


import java.sql.Date;

public class Conge {

  private long idDemande;
  private java.sql.Date dateDemande;
  private long duree;
  private EtatTraitement etat;
  private String justificatif;

  public Conge(long idDemande, Date dateDemande, long duree, EtatTraitement etat, String justificatif) {
    this.idDemande = idDemande;
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    this.justificatif = justificatif;
  }

  public Conge(Date dateDemande, long duree, EtatTraitement etat, String justificatif) {
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    this.justificatif = justificatif;
  }

  public long getIdDemande() {
    return idDemande;
  }

  public void setIdDemande(long idDemande) {
    this.idDemande = idDemande;
  }


  public java.sql.Date getDateDemande() {
    return dateDemande;
  }

  public void setDateDemande(java.sql.Date dateDemande) {
    this.dateDemande = dateDemande;
  }


  public long getDuree() {
    return duree;
  }

  public void setDuree(long duree) {
    this.duree = duree;
  }


  public EtatTraitement getEtat() {
    return etat;
  }

  public void setEtat(EtatTraitement etat) {
    this.etat = etat;
  }


  public String getJustificatif() {
    return justificatif;
  }

  public void setJustificatif(String justificatif) {
    this.justificatif = justificatif;
  }

}
