package com.groupe14ing2.gestioncongesabondants.models;


public class Section {

  private long idSection;
  private long numSection;
  private long idCycle;

  public Section(long idSection, long numSection, long idCycle) {
    this.idSection = idSection;
    this.numSection = numSection;
    this.idCycle = idCycle;
  }

  public Section(long numSection, long idCycle) {
    this.numSection = numSection;
    this.idCycle = idCycle;
  }

  public long getIdSection() {
    return idSection;
  }

  public void setIdSection(long idSection) {
    this.idSection = idSection;
  }


  public long getNumSection() {
    return numSection;
  }

  public void setNumSection(long numSection) {
    this.numSection = numSection;
  }


  public long getIdCycle() {
    return idCycle;
  }

  public void setIdCycle(long idCycle) {
    this.idCycle = idCycle;
  }

}
