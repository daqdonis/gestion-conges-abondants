package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public class Admin implements Serializable {
  private String idAdmin;
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
  public Admin(String idAdmin, String nom, String prenom, RoleAdmin roles, String email, String motPasse) {
    this.idAdmin = idAdmin;
    this.nom = nom;
    this.prenom = prenom;
    this.roles = roles;
    this.email = email;
    this.motPasse = motPasse;
  }



  // Getters and setters
  public String getIdAdmin() {
    return idAdmin;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public RoleAdmin getRoles() {
    return roles;
  }

  public String getEmail() {
    return email;
  }

  public String getMotPasse() {
    return motPasse;
  }

  // Setters
  public void setIdAdmin(String idAdmin) {
    this.idAdmin = idAdmin;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public void setRoles(RoleAdmin roles) {
    this.roles = roles;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setMotPasse(String motPasse) {
    this.motPasse = motPasse;
  }
}