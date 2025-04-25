package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class NoteModule implements Serializable {

  private long idEtu;
  private String idModule;
  private Double noteTd;
  private Double noteTp;
  private Double noteExam;

  public NoteModule(long idEtu, String idModule, double noteTd, double noteTp, double noteExam) {
    this.idEtu = idEtu;
    this.idModule = idModule;
    this.noteTd = noteTd;
    this.noteTp = noteTp;
    this.noteExam = noteExam;
  }

  public NoteModule(long idEtu, String idModule) {
    this.idEtu = idEtu;
    this.idModule = idModule;
    this.noteTd = null;
    this.noteTp = null;
    this.noteExam = null;
  }

  public long getIdEtu() {
    return idEtu;
  }

  public void setIdEtu(long idEtu) {
    this.idEtu = idEtu;
  }


  public String getIdModule() {
    return idModule;
  }

  public void setIdModule(String idModule) {
    this.idModule = idModule;
  }


  public double getNoteTd() {
    return noteTd;
  }

  public void setNoteTd(double noteTd) {
    this.noteTd = noteTd;
  }


  public double getNoteTp() {
    return noteTp;
  }

  public void setNoteTp(double noteTp) {
    this.noteTp = noteTp;
  }


  public double getNoteExam() {
    return noteExam;
  }

  public void setNoteExam(double noteExam) {
    this.noteExam = noteExam;
  }

}
