package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public class Semestre implements Serializable {
  private String idSemestre;
  private int numSemestre;
  private String idCycle;

  public Semestre(String idSemestre, int numSemestre, String idCycle) {
    this.idSemestre = idSemestre;
    this.numSemestre = numSemestre;
    this.idCycle = idCycle;
  }

  public Semestre(int numSemestre, String idCycle) {
    this(null, numSemestre, idCycle);
  }

  // Getters and setters
  public String getIdSemestre() {
    return idSemestre;
  }

  public void setIdSemestre(String idSemestre) {
    this.idSemestre = idSemestre;
  }

  public int getNumSemestre() {
    return numSemestre;
  }

  public void setNumSemestre(int numSemestre) {
    this.numSemestre = numSemestre;
  }

  public String getIdCycle() {
    return idCycle;
  }

  public void setIdCycle(String idCycle) {
    this.idCycle = idCycle;
  }
}