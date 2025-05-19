package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public class Cycle implements Serializable {
  private String idCycle;
  private String designCycle;
  private String idFiliere;

  public Cycle(String idCycle, String designCycle, String idFiliere) {
    this.idCycle = idCycle;
    this.designCycle = designCycle;
    this.idFiliere = idFiliere;
  }

  // Getters and setters
  public String getIdCycle() {
    return idCycle;
  }

  public String getDesignCycle() {
    return designCycle;
  }

  public String getIdFiliere() {
    return idFiliere;
  }

  // Setters
  public void setIdCycle(String idCycle) {
    this.idCycle = idCycle;
  }

  public void setDesignCycle(String designCycle) {
    this.designCycle = designCycle;
  }

  public void setIdFiliere(String idFiliere) {
    this.idFiliere = idFiliere;
  }
}