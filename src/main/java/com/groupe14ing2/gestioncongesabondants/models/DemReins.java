package com.groupe14ing2.gestioncongesabondants.models;


import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;

public class DemReins implements Serializable {

  private long idDemande;
  private java.sql.Date dateDemande;
  private InputStream justificatif;
  private EtatTraitement etat;

  public DemReins(long idDemande, Date dateDemande, FileInputStream justificatif, EtatTraitement etat) {
    this.idDemande = idDemande;
    this.dateDemande = dateDemande;
    this.justificatif = justificatif;
    this.etat = etat;
  }

  public DemReins(Date dateDemande, FileInputStream justificatif, EtatTraitement etat) {
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


  public InputStream getJustificatif() {
    return justificatif;
  }

  public void setJustificatif(FileInputStream justificatif) {
    this.justificatif = justificatif;
  }


  public EtatTraitement getEtat() {
    return etat;
  }

  public void setEtat(EtatTraitement etat) {
    this.etat = etat;
  }



}
