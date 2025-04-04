package com.groupe14ing2.gestioncongesabondants.models;


public class Semestre {

  private long idSemestre;
  private long numSemestre;

  public Semestre(long idSemestre, long numSemestre) {
    this.idSemestre = idSemestre;
    this.numSemestre = numSemestre;
  }

  public Semestre(long numSemestre) {
    this.numSemestre = numSemestre;
  }

  public long getIdSemestre() {
    return idSemestre;
  }

  public void setIdSemestre(long idSemestre) {
    this.idSemestre = idSemestre;
  }


  public long getNumSemestre() {
    return numSemestre;
  }

  public void setNumSemestre(long numSemestre) {
    this.numSemestre = numSemestre;
  }

}
