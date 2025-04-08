package com.groupe14ing2.gestioncongesabondants.models;


import java.sql.Date;

public class Abondant {

  private long idEtu;
  private long idAdmin;
//  Added dateDec
  private Date dateDec;

  public Abondant(int idEtu, int idAdmin, Date dateDec) {
  }


  public long getIdEtu() {
    return idEtu;
  }

  public void setIdEtu(long idEtu) {
    this.idEtu = idEtu;
  }


  public long getIdAdmin() {
    return idAdmin;
  }

  public void setIdAdmin(long idAdmin) {
    this.idAdmin = idAdmin;
  }

  public Date getDateDec() {
    return dateDec;
  }

  public void setDateDec(Date dateDec) {
    this.dateDec = dateDec;
  }

}

