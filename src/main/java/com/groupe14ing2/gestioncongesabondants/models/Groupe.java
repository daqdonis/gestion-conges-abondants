package com.groupe14ing2.gestioncongesabondants.models;


public class Groupe {

  private long idGroupe;
  private long numGroupe;
  private long idSection;
  private long idSemestre;

  public Groupe(long idGroupe, long numGroupe, long idSection, long idSemestre) {
    this.idGroupe = idGroupe;
    this.numGroupe = numGroupe;
    this.idSection = idSection;
    this.idSemestre = idSemestre;
  }

  public Groupe(long numGroupe, long idSection, long idSemestre) {
    this.numGroupe = numGroupe;
    this.idSection = idSection;
    this.idSemestre = idSemestre;
  }

  public long getIdGroupe() {
    return idGroupe;
  }

  public void setIdGroupe(long idGroupe) {
    this.idGroupe = idGroupe;
  }


  public long getNumGroupe() {
    return numGroupe;
  }

  public void setNumGroupe(long numGroupe) {
    this.numGroupe = numGroupe;
  }


  public long getIdSection() {
    return idSection;
  }

  public void setIdSection(long idSection) {
    this.idSection = idSection;
  }


  public long getIdSemestre() {
    return idSemestre;
  }

  public void setIdSemestre(long idSemestre) {
    this.idSemestre = idSemestre;
  }

}
