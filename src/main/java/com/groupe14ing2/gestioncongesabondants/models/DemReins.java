package com.groupe14ing2.gestioncongesabondants.models;


import java.sql.Date;

public class DemReins {

  private long idDemande;
  private java.sql.Date dateDemande;
  private String justificatif;
  private EtatTraitement etat;

  public DemReins(long idDemande, Date dateDemande, String justificatif, EtatTraitement etat) {
    this.idDemande = idDemande;
    this.dateDemande = dateDemande;
    this.justificatif = justificatif;
    this.etat = etat;
  }

  public DemReins(Date dateDemande, String justificatif, EtatTraitement etat) {
    this.dateDemande = dateDemande;
    this.justificatif = justificatif;
    this.etat = etat;
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


  public String getJustificatif() {
    return justificatif;
  }

  public void setJustificatif(String justificatif) {
    this.justificatif = justificatif;
  }


  public EtatTraitement getEtat() {
    return etat;
  }

  public void setEtat(EtatTraitement etat) {
    this.etat = etat;
  }

}
