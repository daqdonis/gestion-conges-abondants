package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public class Section implements Serializable {
  private String idSection;
  private int numSection;
  private String idCycle;

  public Section(String idSection, int numSection, String idCycle) {
    this.idSection = idSection;
    this.numSection = numSection;
    this.idCycle = idCycle;
  }

  public Section(int numSection, String idCycle) {
    this(null, numSection, idCycle);
  }

  // Getters and setters
  public String getIdSection() {
    return idSection;
  }

  public void setIdSection(String idSection) {
    this.idSection = idSection;
  }

  public int getNumSection() {
    return numSection;
  }

  public void setNumSection(int numSection) {
    this.numSection = numSection;
  }

  public String getIdCycle() {
    return idCycle;
  }

  public void setIdCycle(String idCycle) {
    this.idCycle = idCycle;
  }
}