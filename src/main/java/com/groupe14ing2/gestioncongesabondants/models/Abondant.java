package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;
import java.sql.Date;

public class Abondant implements Serializable {
  private long idEtu;
  private String idAdmin;
  private Date dateDec;

  public Abondant(long idEtu, String idAdmin, Date dateDec) {
    this.idEtu = idEtu;
    this.idAdmin = idAdmin;
    this.dateDec = dateDec;
  }

  // Getters and setters
  public long getIdEtu() {
    return idEtu;
  }

  public void setIdEtu(long idEtu) {
    this.idEtu = idEtu;
  }

  public String getIdAdmin() {
    return idAdmin;
  }

  public void setIdAdmin(String idAdmin) {
    this.idAdmin = idAdmin;
  }

  public Date getDateDec() {
    return dateDec;
  }

  public void setDateDec(Date dateDec) {
    this.dateDec = dateDec;
  }
}