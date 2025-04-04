package com.groupe14ing2.gestioncongesabondants.models;


import java.sql.Timestamp;

public class ActionAdmin {

  private long idAdmin;
  private String action;
  private java.sql.Timestamp tempsAction;
  private long idConge;
  private long idReins;
  private long pkAbond;

  public ActionAdmin(long idAdmin, String action, Timestamp tempsAction, long idConge, long idReins, long pkAbond) {
    this.idAdmin = idAdmin;
    this.action = action;
    this.tempsAction = tempsAction;
    this.idConge = idConge;
    this.idReins = idReins;
    this.pkAbond = pkAbond;
  }

  public long getIdAdmin() {
    return idAdmin;
  }

  public void setIdAdmin(long idAdmin) {
    this.idAdmin = idAdmin;
  }


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }


  public java.sql.Timestamp getTempsAction() {
    return tempsAction;
  }

  public void setTempsAction(java.sql.Timestamp tempsAction) {
    this.tempsAction = tempsAction;
  }


  public long getIdConge() {
    return idConge;
  }

  public void setIdConge(long idConge) {
    this.idConge = idConge;
  }


  public long getIdReins() {
    return idReins;
  }

  public void setIdReins(long idReins) {
    this.idReins = idReins;
  }


  public long getPkAbond() {
    return pkAbond;
  }

  public void setPkAbond(long pkAbond) {
    this.pkAbond = pkAbond;
  }

}
