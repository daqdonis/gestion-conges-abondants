package com.groupe14ing2.gestioncongesabondants.models;


import java.sql.Timestamp;

public class ActionAdmin {
  // action here is a string that describes what the admin has done
  private long idAdmin;
  private String action;
  private java.sql.Timestamp tempsAction;
  private Long idConge;
  private Long idReins;
  private Long pkAbond;

  public ActionAdmin(long idAdmin, String action, Timestamp tempsAction, long idActionfait, char actionChar) {
    this.idAdmin = idAdmin;
    this.action = action;
    this.tempsAction = tempsAction;
    this.idConge = null;
    this.idReins = null;
    this.pkAbond = null;

    // switches between A, C and R and sets one of pkAbond, idConge and idReins to idActionFait respectively
    switch (actionChar) {
      case 'A':
        this.pkAbond = idActionfait;
        break;
      case 'C':
        this.idConge = idActionfait;
        break;
      case 'R':
        this.idReins = idActionfait;
        break;
      default:
        throw new IllegalArgumentException("Invalid action char");
    }
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
