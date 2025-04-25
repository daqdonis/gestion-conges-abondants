package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class Filiere implements Serializable {

  private long idFiliere;
  private String designFiliere;

  public Filiere(long idFiliere, String designFiliere) {
    this.idFiliere = idFiliere;
    this.designFiliere = designFiliere;
  }

  public Filiere(String designFiliere) {
    this.designFiliere = designFiliere;
  }

  public long getIdFiliere() {
    return idFiliere;
  }

  public void setIdFiliere(long idFiliere) {
    this.idFiliere = idFiliere;
  }


  public String getDesignFiliere() {
    return designFiliere;
  }

  public void setDesignFiliere(String designFiliere) {
    this.designFiliere = designFiliere;
  }

}
