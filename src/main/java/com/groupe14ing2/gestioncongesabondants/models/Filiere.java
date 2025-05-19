package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class Filiere implements Serializable {

  private String idFiliere;
  private String designFiliere;

  public Filiere(String idFiliere, String designFiliere) {
    this.idFiliere = idFiliere;
    this.designFiliere = designFiliere;
  }

  public Filiere(String designFiliere) {
    this.designFiliere = designFiliere;
  }

  public String getIdFiliere() {
    return idFiliere;
  }

  public void setIdFiliere(String idFiliere) {
    this.idFiliere = idFiliere;
  }


  public String getDesignFiliere() {
    return designFiliere;
  }

  public void setDesignFiliere(String designFiliere) {
    this.designFiliere = designFiliere;
  }

}
