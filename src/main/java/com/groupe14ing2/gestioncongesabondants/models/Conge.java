package com.groupe14ing2.gestioncongesabondants.models;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

public class Conge implements Serializable {

  private static final long serialVersionUID = 1L;

  private String idDemande;
  private long idEtu;
  private Date dateDemande;
  private int duree;
  private EtatTraitement etat;
  private TypeConge type;
  private transient File justificatif;
  private Etudiant etudiant;
  private byte[] justificatifData;


  public Conge(String idDemande, Date dateDemande, int duree, EtatTraitement etat, InputStream justificatif, TypeConge type) {
    this.idDemande = idDemande;
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    System.out.println(type);
    this.type = type;
    createJustificatifFile(justificatif);
  }
  public Conge(Date dateDemande, int duree, EtatTraitement etat, InputStream justificatif, TypeConge type) {
    this.idDemande = null;
    this.dateDemande = dateDemande;
    this.duree = duree;
    this.etat = etat;
    this.type = type;
    createJustificatifFile(justificatif);
  }

  public String getIdDemande() {
    return idDemande;
  }

  public long getIdEtu() {
    return idEtu;
  }

  public Date getDateDemande() {
    return dateDemande;
  }

  public int getDuree() {
    return duree;
  }

  public EtatTraitement getEtat() {
    return etat;
  }

  public TypeConge getType() {
    return type;
  }

  public File getJustificatif() {
    return justificatif;
  }

  public Etudiant getEtudiant() {
    return etudiant;
  }

  // Setters
  public void setIdDemande(String idDemande) {
    this.idDemande = idDemande;
  }

  public void setDateDemande(Date dateDemande) {
    this.dateDemande = dateDemande;
  }

  public void setDuree(int duree) {
    this.duree = duree;
  }

  public void setEtat(EtatTraitement etat) {
    this.etat = etat;
  }

  public void setType(TypeConge type) {
    this.type = type;
  }

  public void setEtudiant(Etudiant etudiant) {
    this.etudiant = etudiant;
  }

  private void createJustificatifFile(InputStream justificatif) {
    try {
      this.justificatif = File.createTempFile("conge", ".pdf");
      Files.copy(justificatif, this.justificatif.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

