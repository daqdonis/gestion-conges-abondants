package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActionAdmin implements Serializable {
  private int idAction;
  private String idAdmin;
  private String action;
  private Timestamp tempsAction;
  private String idConge = null;
  private String idReins = null;
  private Long pkAbond = -1L;

  public ActionAdmin(int idAction, String idAdmin, String action, Timestamp tempsAction,
                     String idConge, String idReins, long pkAbond) {
    this.idAction = idAction;
    this.idAdmin = idAdmin;
    this.action = action;
    this.tempsAction = tempsAction;
    this.idConge = idConge;
    this.idReins = idReins;
    this.pkAbond = pkAbond;
  }

  // Alternative constructor for creating actions
  public ActionAdmin(String idAdmin, String action, String idActionfait, char actionChar) {
    this.idAdmin = idAdmin;
    this.action = action;
    this.tempsAction = new Timestamp(System.currentTimeMillis());
    switch (actionChar) {
      case 'A':
        this.pkAbond = Long.parseLong(idActionfait);
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

  // Getters and setters
  public int getIdAction() {
    return idAction;
  }

  public String getIdAdmin() {
    return idAdmin;
  }

  public String getAction() {
    return action;
  }

  public Timestamp getTempsAction() {
    return tempsAction;
  }

  public String getIdConge() {
    return idConge;
  }

  public String getIdReins() {
    return idReins;
  }

  public long getPkAbond() {
    return pkAbond;
  }

  // Setters
  public void setIdAction(int idAction) {
    this.idAction = idAction;
  }

  public void setIdAdmin(String idAdmin) {
    this.idAdmin = idAdmin;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setTempsAction(Timestamp tempsAction) {
    this.tempsAction = tempsAction;
  }

  public void setIdConge(String idConge) {
    this.idConge = idConge;
  }

  public void setIdReins(String idReins) {
    this.idReins = idReins;
  }

  public void setPkAbond(long pkAbond) {
    this.pkAbond = pkAbond;
  }
}