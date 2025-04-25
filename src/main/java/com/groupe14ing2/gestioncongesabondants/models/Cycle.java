package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class Cycle implements Serializable {

  private long idCycle;
  private String designCycle;
  private long idFiliere;

  public Cycle(long idCycle, String designCycle, long idFiliere) {
    this.idCycle = idCycle;
    this.designCycle = designCycle;
    this.idFiliere = idFiliere;
  }

  public Cycle(String designCycle, long idFiliere) {
    this.designCycle = designCycle;
    this.idFiliere = idFiliere;
  }

  public long getIdCycle() {
    return idCycle;
  }

  public void setIdCycle(long idCycle) {
    this.idCycle = idCycle;
  }


  public String getDesignCycle() {
    return designCycle;
  }

  public void setDesignCycle(String designCycle) {
    this.designCycle = designCycle;
  }


  public long getIdFiliere() {
    return idFiliere;
  }

  public void setIdFiliere(long idFiliere) {
    this.idFiliere = idFiliere;
  }

}
