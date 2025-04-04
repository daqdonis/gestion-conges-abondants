package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseController {
    private static final String URL = "jdbc:mysql://localhost:3306/conges_abondant"; // Change to your MySQL URL
    private static final String USER = "username"; // Change to your MySQL username
    private static final String PASSWORD = "password"; // Change to your MySQL password
    private Connection connection;

    public DatabaseController() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // adds a new admin account
    public void addAdmin(Admin admin) throws SQLException {
           String sql = "INSERT INTO conges_abondant.Admin\n" +
                   "(nom, prenom, roles, email, mot_passe)\n" +
                   "VALUES(?, ?, ?, ?, ?);";

           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, admin.getNom());
           preparedStatement.setString(2, admin.getPrenom());
           preparedStatement.setString(3, admin.getRoles().toString());
           preparedStatement.setString(4, admin.getEmail());
           preparedStatement.setString(5, admin.getMotPasse());
           preparedStatement.executeUpdate();

           preparedStatement.executeUpdate();
    }

    // removes an admin account
    public void removeAdmin(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.Admin WHERE id_admin = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    // adds a new student to the db
    public void addEtudiant(Etudiant etudiant) throws SQLException {
        String sql = "INSERT INTO conges_abondant.Etudiant\n" +
                "(id_etu, nom, prenom, date_naiss, id_groupe, id_conge, id_dem_reins)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, etudiant.getNom());
        preparedStatement.setString(2, etudiant.getPrenom());
        preparedStatement.setDate(3, etudiant.getDateNaiss());
        preparedStatement.setInt(4, (int)etudiant.getIdGroupe());
        preparedStatement.setInt(5, (int)etudiant.getIdConge());
        preparedStatement.setInt(6, (int)etudiant.getIdDemReins());

        preparedStatement.executeUpdate();
    }

    // removes a student from the db
    public void removeEtudiant(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.Etudiant WHERE id_etu = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    // TODO: add other methods to add new entities to the database

}
