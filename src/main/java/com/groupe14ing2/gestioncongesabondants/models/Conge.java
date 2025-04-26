package com.groupe14ing2.gestioncongesabondants.models;


import java.io.*;
import java.sql.Date;

public class Conge implements Serializable {

  private static final long serialVersionUID = 1L;

  private long idDemande;
  private java.sql.Date dateDemande;
  private long duree;
  private EtatTraitement etat;
  private transient InputStream justificatif;
  private byte[] justificatifData; // For serialization

  public Conge(long idDemande, Date dateDemande, long duree, EtatTraitement etat, FileInputStream justificatif) {
    this.idDemande = idDemande;
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    this.justificatif = justificatif;
  }

  public Conge(Date dateDemande, long duree, EtatTraitement etat, FileInputStream justificatif) {
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    this.justificatif = justificatif;
  }
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    if (justificatif != null) {
      justificatifData = justificatif.readAllBytes();
      out.writeObject(justificatifData);
    }
  }
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    justificatifData = (byte[]) in.readObject();
    if (justificatifData != null) {
      justificatif = new ByteArrayInputStream(justificatifData);
    }
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


  public InputStream getJustificatif() {
    return justificatif;
  }

  public void setJustificatif(FileInputStream justificatif) {
    this.justificatif = justificatif;
  }

}
