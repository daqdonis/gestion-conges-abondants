package com.groupe14ing2.gestioncongesabondants.models;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;

public class DemReins implements Serializable {
  private String idDemande;
  private long idEtu;
  private Date dateDemande;
  private InputStream justificatif;
  private EtatTraitement etat;

  public DemReins(String idDemande, long idEtu, Date dateDemande, FileInputStream justificatif, EtatTraitement etat) {
    this.idDemande = idDemande;
    this.idEtu = idEtu;
    this.dateDemande = dateDemande;
    this.justificatif = justificatif;
    this.etat = etat;
  }

  public DemReins(long idEtu, Date dateDemande, FileInputStream justificatif, EtatTraitement etat) {
    this(null, idEtu, dateDemande, justificatif, etat);
  }

  // Getters and setters
  public String getIdDemande() {
    return idDemande;
  }

  public void setIdDemande(String idDemande) {
    this.idDemande = idDemande;
  }

  public long getIdEtu() {
    return idEtu;
  }

  public void setIdEtu(long idEtu) {
    this.idEtu = idEtu;
  }

  public Date getDateDemande() {
    return dateDemande;
  }

  public void setDateDemande(Date dateDemande) {
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