package com.groupe14ing2.gestioncongesabondants.models;


public class Module {

  private String idModule;
  private String nomModule;
  private long idSemestre;

  public Module(String idModule, String nomModule, long idSemestre) {
    this.idModule = idModule;
    this.nomModule = nomModule;
    this.idSemestre = idSemestre;
  }

  public Module(String nomModule, long idSemestre) {
    this.nomModule = nomModule;
    this.idSemestre = idSemestre;
  }

  public String getIdModule() {
    return idModule;
  }

  public void setIdModule(String idModule) {
    this.idModule = idModule;
  }


  public String getNomModule() {
    return nomModule;
  }

  public void setNomModule(String nomModule) {
    this.nomModule = nomModule;
  }


  public long getIdSemestre() {
    return idSemestre;
  }

  public void setIdSemestre(long idSemestre) {
    this.idSemestre = idSemestre;
  }

}
