package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.*;
import com.groupe14ing2.gestioncongesabondants.models.Module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseController {
    private static final String URL = "jdbc:mysql://localhost:3306/conges_abondant"; // Change to your MySQL URL
    private static final String USER = "username"; // Change to your MySQL username
    private static final String PASSWORD = "password"; // Change to your MySQL password
    private final Connection connection;

    public DatabaseController() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // adds a new admin account
    public void addAdmin(Admin admin) throws SQLException {
           String sql = """
                   INSERT INTO conges_abondant.Admin
                   (nom, prenom, roles, email, mot_passe)
                   VALUES(?, ?, ?, ?, ?);""";

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
        String sql = """
                INSERT INTO conges_abondant.Etudiant
                (id_etu, nom, prenom, date_naiss, id_groupe, id_conge, id_dem_reins)
                VALUES(?, ?, ?, ?, ?, ?, ?);""";

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

    // adds a new cycle to the db
    public void addCycle(Cycle cycle) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Cycle`
                (design_cycle, id_filiere)
                VALUES(?, ?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cycle.getDesignCycle());
        preparedStatement.setInt(2, (int)cycle.getIdFiliere());

        preparedStatement.executeUpdate();
    }

    // remove a cycle
    public void removeCycle(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Cycle`\n" +
                "WHERE id_cycle=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    // adds a filiere to th db
    public void addFiliere(Filiere filiere) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Filiere`
                (design_filiere)
                VALUES(?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filiere.getDesignFiliere());

        preparedStatement.executeUpdate();
    }

    // removes a filiere from the db
    public void removeFiliere(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Filiere` WHERE id_filiere=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    // adds a semestre
    public void addSemestre(Semestre semestre) throws SQLException {
        String sql = "INSERT INTO conges_abondant.`Semestre`\n" +
                "(num_semestre)\n" +
                "VALUES(?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)semestre.getNumSemestre());
        preparedStatement.executeUpdate();
    }

    public void removeSemestre(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Semestre` WHERE id_semestre=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addGroupe(Groupe groupe) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Groupe`
                (num_groupe, id_section, id_semestre)
                VALUES(?,?,?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)groupe.getNumGroupe());
        preparedStatement.setInt(2, (int)groupe.getIdSection());
        preparedStatement.setInt(3, (int)groupe.getIdSemestre());
        preparedStatement.executeUpdate();
    }

    public void removeGroupe(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Groupe` WHERE id_groupe=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addConge(Conge conge) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Conge`
                (date_demande, duree, etat, justificatif)
                VALUES(?, ?,?,?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, conge.getDateDemande());
        preparedStatement.setInt(2, (int)conge.getDuree());
        preparedStatement.setString(3, conge.getEtat().toString());
        preparedStatement.setBlob(4, conge.getJustificatif());

        preparedStatement.executeUpdate();
    }

    public void removeConge(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Conge` WHERE id_demande=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addSection(Section section) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Section`
                (num_section, id_cycle)
                VALUES(?, ?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)section.getNumSection());
        preparedStatement.setInt(2, (int)section.getIdCycle());

        preparedStatement.executeUpdate();

    }

    public void removeSection(int id) throws SQLException {
        String sql = "DELETE FROM conges_abondant.`Section` WHERE id_section=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addModule(Module module) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Module`
                (id_module, nom_module, id_semestre)
                VALUES(?, ?, ?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, module.getIdModule());
        preparedStatement.setString(2, module.getNomModule());
        preparedStatement.setInt(3, (int)module.getIdSemestre());

        preparedStatement.executeUpdate();
    }

    public void removeModule(int id) throws SQLException {
        String sql = """
                DELETE FROM conges_abondant.`Module`
                WHERE id_module=?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addNoteModule(NoteModule noteModule) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Note_module`
                (id_etu, id_module, note_td, note_tp, note_exam)
                VALUES(?, ?, ?, ?, ?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)noteModule.getIdEtu());
        preparedStatement.setString(2, noteModule.getIdModule());
        preparedStatement.setFloat(3, (float)noteModule.getNoteTd());
        preparedStatement.setFloat(4, (float)noteModule.getNoteTp());
        preparedStatement.setFloat(5, (float)noteModule.getNoteExam());

        preparedStatement.executeUpdate();
    }

    public void removeNoteModule(Etudiant etudiant, Module module) throws SQLException {
        String sql = """
                DELETE FROM conges_abondant.`Note_module`
                WHERE id_module=? AND id_etu=?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)etudiant.getIdEtu());
        preparedStatement.setString(2, module.getIdModule());

        preparedStatement.executeUpdate();
    }

    public void addDemReins(DemReins demReins) throws SQLException {
        String sql = """
                INSERT INTO conges_abondant.`Dem_reins`
                (date_demande, justificatif, etat)
                VALUES(?, ?, ?);""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, demReins.getDateDemande());
        preparedStatement.setBlob(2, demReins.getJustificatif());
        preparedStatement.setString(3, demReins.getEtat().toString());
        preparedStatement.executeUpdate();
    }

    public void removeDemReins(int id) throws SQLException {
        String sql  = "DELETE FROM conges_abondant.`Dem_reins` WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void addAbondant(Abondant abondant) throws SQLException {
        String sql = """
        INSERT INTO conges_abondant.`Abondant`
        (id_etu, id_admin, date_dec)
        VALUES (?,?,?)""";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
    }

    public void removeAbondant(Etudiant etudiant) throws SQLException {
        String sql = "DELETE FROM conges_abondant.Abondant WHERE id_etu = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)etudiant.getIdEtu());
        preparedStatement.executeUpdate();
    }

    public void addActionAdmin(ActionAdmin actionAdmin) throws SQLException {
        String sql = """
        INSERT INTO conges_abondant.`Action_admin`
        (id_admin, action, temps_action, id_conge, id_reins, pk_abond)
        VALUES (?,?,?,?,?,?)
        """;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)actionAdmin.getIdAdmin());
        preparedStatement.setString(2, actionAdmin.getAction());
        preparedStatement.setTimestamp(3, actionAdmin.getTempsAction());
        preparedStatement.setInt(4, (int)actionAdmin.getIdConge());
        preparedStatement.setInt(5, (int)actionAdmin.getIdReins());
        preparedStatement.setInt(6, (int)actionAdmin.getPkAbond());

        preparedStatement.executeUpdate();
    }

    //  TODO: add update and get methods for all the entities

}
