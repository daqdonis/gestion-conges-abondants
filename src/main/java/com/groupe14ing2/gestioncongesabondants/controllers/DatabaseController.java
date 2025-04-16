package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.*;
import com.groupe14ing2.gestioncongesabondants.models.Module;

import java.io.FileInputStream;
import java.sql.*;

public class DatabaseController extends DatabaseLink{

    public DatabaseController() throws SQLException {
        super();
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

    //  TODO: add update and get methods for all the entities (Done)

    // NEW METHODS ADDED

    // Admin methods
    public void updateAdmin(Admin admin) throws SQLException {
        String sql = """
            UPDATE conges_abondant.Admin
            SET nom = ?, prenom = ?, roles = ?, email = ?, mot_passe = ?
            WHERE id_admin = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, admin.getNom());
        preparedStatement.setString(2, admin.getPrenom());
        preparedStatement.setString(3, admin.getRoles().toString());
        preparedStatement.setString(4, admin.getEmail());
        preparedStatement.setString(5, admin.getMotPasse());
        preparedStatement.setInt(6, (int)admin.getIdAdmin());
        preparedStatement.executeUpdate();
    }

    public Admin getAdmin(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.Admin WHERE id_admin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Admin(
                    resultSet.getInt("id_admin"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    RoleAdmin.valueOf(resultSet.getString("roles")),
                    resultSet.getString("email"),
                    resultSet.getString("mot_passe")
            );
        }
        return null;
    }

    // Etudiant methods
    public void updateEtudiant(Etudiant etudiant) throws SQLException {
        String sql = """
            UPDATE conges_abondant.Etudiant
            SET nom = ?, prenom = ?, date_naiss = ?, id_groupe = ?, id_conge = ?, id_dem_reins = ?
            WHERE id_etu = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, etudiant.getNom());
        preparedStatement.setString(2, etudiant.getPrenom());
        preparedStatement.setDate(3, etudiant.getDateNaiss());
        preparedStatement.setInt(4, (int)etudiant.getIdGroupe());
        preparedStatement.setInt(5, (int)etudiant.getIdConge());
        preparedStatement.setInt(6, (int)etudiant.getIdDemReins());
        preparedStatement.setInt(7, (int)etudiant.getIdEtu());
        preparedStatement.executeUpdate();
    }

    public Etudiant getEtudiant(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.Etudiant WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Etudiant(
                    resultSet.getInt("id_etu"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getDate("date_naiss"),
                    resultSet.getInt("id_groupe"),
                    resultSet.getInt("id_conge"),
                    resultSet.getInt("id_dem_reins")
            );
        }
        return null;
    }

    public void updateCycle(Cycle cycle) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Cycle`
            SET design_cycle = ?, id_filiere = ?
            WHERE id_cycle = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cycle.getDesignCycle());
        preparedStatement.setInt(2, (int)cycle.getIdFiliere());
        preparedStatement.setInt(3, (int)cycle.getIdCycle());
        preparedStatement.executeUpdate();
    }

    public Cycle getCycle(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Cycle` WHERE id_cycle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Cycle(
                    resultSet.getInt("id_cycle"),
                    resultSet.getString("design_cycle"),
                    resultSet.getInt("id_filiere")
            );
        }
        return null;
    }




    // Filiere Methods
    public void updateFiliere(Filiere filiere) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Filiere`
            SET design_filiere = ?
            WHERE id_filiere = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filiere.getDesignFiliere());
        preparedStatement.setInt(2, (int)filiere.getIdFiliere());
        preparedStatement.executeUpdate();
    }

    public Filiere getFiliere(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Filiere` WHERE id_filiere = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Filiere(
                    resultSet.getInt("id_filiere"),
                    resultSet.getString("design_filiere")
            );
        }
        return null;
    }

    //Semestre Methods
    public void updateSemestre(Semestre semestre) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Semestre`
            SET num_semestre = ?
            WHERE id_semestre = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)semestre.getNumSemestre());
        preparedStatement.setInt(2, (int)semestre.getIdSemestre());
        preparedStatement.executeUpdate();
    }

    public Semestre getSemestre(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Semestre` WHERE id_semestre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Semestre(
                    resultSet.getInt("id_semestre"),
                    resultSet.getInt("num_semestre")
            );
        }
        return null;
    }

    //Groupe Methods
    public void updateGroupe(Groupe groupe) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Groupe`
            SET num_groupe = ?, id_section = ?, id_semestre = ?
            WHERE id_groupe = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)groupe.getNumGroupe());
        preparedStatement.setInt(2, (int)groupe.getIdSection());
        preparedStatement.setInt(3, (int)groupe.getIdSemestre());
        preparedStatement.setInt(4, (int)groupe.getIdGroupe());
        preparedStatement.executeUpdate();
    }

    public Groupe getGroupe(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Groupe` WHERE id_groupe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Groupe(
                    resultSet.getInt("id_groupe"),
                    resultSet.getInt("num_groupe"),
                    resultSet.getInt("id_section"),
                    resultSet.getInt("id_semestre")
            );
        }
        return null;
    }

    // Conge Methods
    public void updateConge(Conge conge) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Conge`
            SET date_demande = ?, duree = ?, etat = ?, justificatif = ?
            WHERE id_demande = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, conge.getDateDemande());
        preparedStatement.setInt(2, (int)conge.getDuree());
        preparedStatement.setString(3, conge.getEtat().toString());
        preparedStatement.setBlob(4, conge.getJustificatif());
        preparedStatement.setInt(5, (int)conge.getIdDemande());
        preparedStatement.executeUpdate();
    }

    public Conge getConge(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Conge` WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Conge(
                    resultSet.getInt("id_demande"),
                    resultSet.getDate("date_demande"),
                    resultSet.getInt("duree"),
                    EtatTraitement.valueOf(resultSet.getString("etat")),
                    (FileInputStream) resultSet.getBlob("justificatif")
            );
        }
        return null;
    }

    //Section Methods
    public void updateSection(Section section) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Section`
            SET num_section = ?, id_cycle = ?
            WHERE id_section = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)section.getNumSection());
        preparedStatement.setInt(2, (int)section.getIdCycle());
        preparedStatement.setInt(3, (int)section.getIdSection());
        preparedStatement.executeUpdate();
    }

    public Section getSection(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Section` WHERE id_section = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Section(
                    resultSet.getInt("id_section"),
                    resultSet.getInt("num_section"),
                    resultSet.getInt("id_cycle")
            );
        }
        return null;
    }

    // Module Methods
    public void updateModule(Module module) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Module`
            SET nom_module = ?, id_semestre = ?
            WHERE id_module = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, module.getNomModule());
        preparedStatement.setInt(2, (int)module.getIdSemestre());
        preparedStatement.setString(3, module.getIdModule());
        preparedStatement.executeUpdate();
    }

    public Module getModule(String id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Module` WHERE id_module = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Module(
                    resultSet.getString("id_module"),
                    resultSet.getString("nom_module"),
                    resultSet.getInt("id_semestre")
            );
        }
        return null;
    }

    //NoteModule Methods
    public void updateNoteModule(NoteModule noteModule) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Note_module`
            SET note_td = ?, note_tp = ?, note_exam = ?
            WHERE id_etu = ? AND id_module = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, (float)noteModule.getNoteTd());
        preparedStatement.setFloat(2, (float)noteModule.getNoteTp());
        preparedStatement.setFloat(3, (float)noteModule.getNoteExam());
        preparedStatement.setInt(4, (int)noteModule.getIdEtu());
        preparedStatement.setString(5, noteModule.getIdModule());
        preparedStatement.executeUpdate();
    }

    public NoteModule getNoteModule(int etudiantId, String moduleId) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Note_module` WHERE id_etu = ? AND id_module = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, etudiantId);
        preparedStatement.setString(2, moduleId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new NoteModule(
                    resultSet.getInt("id_etu"),
                    resultSet.getString("id_module"),
                    resultSet.getFloat("note_td"),
                    resultSet.getFloat("note_tp"),
                    resultSet.getFloat("note_exam")
            );
        }
        return null;
    }

    //DemReins Methods
    public void updateDemReins(DemReins demReins) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Dem_reins`
            SET date_demande = ?, justificatif = ?, etat = ?
            WHERE id_demande = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, demReins.getDateDemande());
        preparedStatement.setBlob(2, demReins.getJustificatif());
        preparedStatement.setString(3, demReins.getEtat().toString());
        preparedStatement.setInt(4, (int)demReins.getIdDemande());
        preparedStatement.executeUpdate();
    }

    public DemReins getDemReins(int id) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Dem_reins` WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new DemReins(
                    resultSet.getInt("id_demande"),
                    resultSet.getDate("date_demande"),
                    (FileInputStream) resultSet.getBlob("justificatif"),
                    EtatTraitement.valueOf(resultSet.getString("etat"))
            );
        }
        return null;
    }

    //Abondant Methods
    public void updateAbondant(Abondant abondant) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Abondant`
            SET id_admin = ?, date_dec = ?
            WHERE id_etu = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)abondant.getIdAdmin());
        preparedStatement.setDate(2, new java.sql.Date(abondant.getDateDec().getTime()));
        preparedStatement.setInt(3, (int)abondant.getIdEtu());
        preparedStatement.executeUpdate();
    }

    public Abondant getAbondant(int etudiantId) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Abondant` WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, etudiantId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Abondant(
                    resultSet.getInt("id_etu"),
                    resultSet.getInt("id_admin"),
                    resultSet.getDate("date_dec")
            );
        }
        return null;
    }

    //ActionAdmin Methods
    /*public void updateActionAdmin(ActionAdmin actionAdmin) throws SQLException {
        String sql = """
            UPDATE conges_abondant.`Action_admin`
            SET action = ?, temps_action = ?, id_conge = ?, id_reins = ?, pk_abond = ?
            WHERE id_action = ?;""";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, actionAdmin.getAction());
        preparedStatement.setTimestamp(2, actionAdmin.getTempsAction());
        preparedStatement.setInt(3, (int)actionAdmin.getIdConge());
        preparedStatement.setInt(4, (int)actionAdmin.getIdReins());
        preparedStatement.setInt(5, (int)actionAdmin.getPkAbond());
        preparedStatement.setInt(6, (int)actionAdmin.getIdAction());
        preparedStatement.executeUpdate();
    }*/

    public ActionAdmin getAllActionAdmin(int idAdmin) throws SQLException {
        String sql = "SELECT * FROM conges_abondant.`Action_admin` WHERE id_admin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idAdmin);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new ActionAdmin(
                    resultSet.getInt("id_action"),
                    resultSet.getInt("id_admin"),
                    resultSet.getString("action"),
                    resultSet.getTimestamp("temps_action"),
                    resultSet.getInt("id_conge"),
                    resultSet.getInt("id_reins"),
                    resultSet.getInt("pk_abond")
            );
        }
        return null;
    }


}
