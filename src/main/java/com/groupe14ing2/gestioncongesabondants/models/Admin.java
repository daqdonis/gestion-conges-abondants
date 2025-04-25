package com.groupe14ing2.gestioncongesabondants.models;


import java.io.Serializable;

public class Admin implements Serializable {

  private long idAdmin;
  private String nom;
  private String prenom;
  private RoleAdmin roles;
  private String email;
  private String motPasse;

  public Admin(String nom, String prenom, RoleAdmin roles, String email, String motPasse) {
    this.nom = nom;
    this.prenom = prenom;
    this.roles = roles;
    this.email = email;
    this.motPasse = motPasse;
  }

  public Admin(long idAdmin, String nom, String prenom, RoleAdmin roles, String email, String motPasse) {
    this.idAdmin = idAdmin;
    this.nom = nom;
    this.prenom = prenom;
    this.roles = roles;
    this.email = email;
    this.motPasse = motPasse;
  }

  public long getIdAdmin() {
    return idAdmin;
  }

  public void setIdAdmin(long idAdmin) {
    this.idAdmin = idAdmin;
  }


  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }


  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }


  public RoleAdmin getRoles() {
    return roles;
  }

  public void setRoles(RoleAdmin roles) {
    this.roles = roles;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getMotPasse() {
    return motPasse;
  }

  public void setMotPasse(String motPasse) {
    this.motPasse = motPasse;
  }

}
