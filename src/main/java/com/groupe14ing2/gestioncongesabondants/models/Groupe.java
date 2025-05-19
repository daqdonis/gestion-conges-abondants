package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class Groupe implements Serializable {

  private String idGroupe;
  private long numGroupe;
  private String idSection;
  private String idSemestrePair;
  private String idSemestreImpair;

  public Groupe(String idGroupe, long numGroupe, String idSection, String idSemestrePair, String idSemestreImpair) {
    this.idGroupe = idGroupe;
    this.numGroupe = numGroupe;
    this.idSection = idSection;
    this.idSemestrePair = idSemestrePair;
    this.idSemestreImpair = idSemestreImpair;
  }

  public Groupe(long numGroupe, String idSection, String idSemestre) {
    this.numGroupe = numGroupe;
    this.idSection = idSection;
    this.idSemestrePair = idSemestre;
  }

  public String getIdGroupe() {
    return idGroupe;
  }

  public void setIdGroupe(String idGroupe) {
    this.idGroupe = idGroupe;
  }


  public long getNumGroupe() {
    return numGroupe;
  }

  public void setNumGroupe(long numGroupe) {
    this.numGroupe = numGroupe;
  }


  public String getIdSection() {
    return idSection;
  }

  public void setIdSection(String idSection) {
    this.idSection = idSection;
  }

  public void setIdSemestre(String idSemestre) {
    this.idSemestrePair = idSemestre;
  }

  public String getIdSemestrePair() {
    return idSemestrePair;
  }

  public void setIdSemestrePair(String idSemestrePair) {
    this.idSemestrePair = idSemestrePair;
  }

  public String getIdSemestreImpair() {
    return idSemestreImpair;
  }

  public void setIdSemestreImpair(String idSemestreImpair) {
    this.idSemestreImpair = idSemestreImpair;
  }
}
