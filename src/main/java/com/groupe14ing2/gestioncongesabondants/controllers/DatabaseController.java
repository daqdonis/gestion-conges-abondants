package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.groupe14ing2.gestioncongesabondants.utils.PasswordUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.groupe14ing2.gestioncongesabondants.models.Abondant;
import com.groupe14ing2.gestioncongesabondants.models.ActionAdmin;
import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.Cycle;
import com.groupe14ing2.gestioncongesabondants.models.DemReins;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import com.groupe14ing2.gestioncongesabondants.models.Filiere;
import com.groupe14ing2.gestioncongesabondants.models.Groupe;
import com.groupe14ing2.gestioncongesabondants.models.RoleAdmin;
import com.groupe14ing2.gestioncongesabondants.models.Section;
import com.groupe14ing2.gestioncongesabondants.models.Semestre;
import com.groupe14ing2.gestioncongesabondants.models.TypeConge;

public class DatabaseController extends DatabaseLink {

    public DatabaseController() throws SQLException {
        super();
    }

    // Admin methods
    public void addAdmin(Admin admin) throws SQLException {
        String sql = "INSERT IGNORE INTO Admin (id_admin, nom, prenom, roles, email, mot_passe) VALUES(?, ?, ?, ?, ?, ?)";

        // Generate admin ID based on role
        String idPrefix;
        switch (admin.getRoles()) {
            case ADMINCOMPTES:
                idPrefix = "AC";
                break;
            case ADMINCONGEABANDONT:
                idPrefix = "AA";
                break;
            default:
                throw new SQLException("Invalid role");
        }

        // Get the next available ID number
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Admin WHERE id_admin LIKE ?");
        ps.setString(1, idPrefix + "%");
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        String id = idPrefix + "-" + String.format("%03d", resultSet.getInt("COUNT(*)") + 1);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, admin.getNom());
        preparedStatement.setString(3, admin.getPrenom());
        preparedStatement.setString(4, admin.getRoles().toString().toLowerCase());
        preparedStatement.setString(5, admin.getEmail());
        preparedStatement.setString(6, PasswordUtils.hashPassword(admin.getMotPasse()));

        preparedStatement.executeUpdate();
    }

    public void removeAdmin(String id) throws SQLException {
        String sql = "DELETE FROM Admin WHERE id_admin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateAdmin(Admin admin) throws SQLException {
        String sql = "UPDATE Admin SET nom = ?, prenom = ?, roles = ?, email = ?, mot_passe = ? WHERE id_admin = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, admin.getNom());
        preparedStatement.setString(2, admin.getPrenom());
        preparedStatement.setString(3, admin.getRoles().toString().toLowerCase());
        preparedStatement.setString(4, admin.getEmail());
        preparedStatement.setString(5, PasswordUtils.hashPassword(admin.getMotPasse()));
        preparedStatement.setString(6, admin.getIdAdmin());

        preparedStatement.executeUpdate();
    }

    public Admin getAdmin(String id) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE id_admin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Admin(
                    resultSet.getString("id_admin"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    RoleAdmin.valueOf(resultSet.getString("roles").toUpperCase().replace("ADMIN_", "ADMIN")),
                    resultSet.getString("email"),
                    null
            );
        }
        return null;
    }

    public List<Admin> getAllAdmins() throws SQLException {
        String sql = "SELECT * FROM Admin";
        List<Admin> admins = new ArrayList<>();

        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            admins.add(
                    new Admin(
                            rs.getString("id_admin"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            RoleAdmin.valueOf(rs.getString("roles").toUpperCase().replaceAll("_", "")),
                            rs.getString("email"),
                            null
                    )
            );
        }
        return admins;
    }

    public void addEtudiant(Etudiant etudiant) throws SQLException {
        String sql = "INSERT IGNORE INTO Etudiant (id_etu, nom, prenom, date_naiss, id_groupe, email_etu) VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, etudiant.getIdEtu());
        preparedStatement.setString(2, etudiant.getNom());
        preparedStatement.setString(3, etudiant.getPrenom());
        preparedStatement.setDate(4, etudiant.getDateNaiss());
        preparedStatement.setString(5, etudiant.getIdGroupe());
        preparedStatement.setString(6, etudiant.getemail_etu());

        preparedStatement.executeUpdate();
    }

    public void removeEtudiant(long id) throws SQLException {
        String sql = "DELETE FROM Etudiant WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateEtudiant(Etudiant etudiant) throws SQLException {
        String sql = "UPDATE Etudiant SET nom = ?, prenom = ?, date_naiss = ?, id_groupe = ?, email_etu = ? WHERE id_etu = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, etudiant.getNom());
        preparedStatement.setString(2, etudiant.getPrenom());
        preparedStatement.setDate(3, etudiant.getDateNaiss());
        preparedStatement.setString(4, etudiant.getIdGroupe());
        preparedStatement.setLong(5, etudiant.getIdEtu());
        preparedStatement.setString(6, etudiant.getIdGroupe());


        preparedStatement.executeUpdate();
    }

    public Etudiant getEtudiant(long id) throws SQLException {
        String sql = "SELECT * FROM Etudiant WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Etudiant(
                    resultSet.getLong("id_etu"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getDate("date_naiss"),
                    resultSet.getString("id_groupe"),
                    resultSet.getString("email_etu")
            );
        }
        return null;
    }

    // Filiere methods
    public void addFiliere(Filiere filiere) throws SQLException {
        String sql = "INSERT IGNORE INTO Filiere (id_filiere, design_filiere) VALUES(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filiere.getIdFiliere());
        preparedStatement.setString(2, filiere.getDesignFiliere());

        preparedStatement.executeUpdate();
    }

    public void removeFiliere(String id) throws SQLException {
        String sql = "DELETE FROM Filiere WHERE id_filiere = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateFiliere(Filiere filiere) throws SQLException {
        String sql = "UPDATE Filiere SET design_filiere = ? WHERE id_filiere = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filiere.getDesignFiliere());
        preparedStatement.setString(2, filiere.getIdFiliere());

        preparedStatement.executeUpdate();
    }

    public Filiere getFiliere(String id) throws SQLException {
        String sql = "SELECT * FROM Filiere WHERE id_filiere = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Filiere(
                    resultSet.getString("id_filiere"),
                    resultSet.getString("design_filiere")
            );
        }
        return null;
    }

    // Cycle methods
    public void addCycle(Cycle cycle) throws SQLException {
        String sql = "INSERT IGNORE INTO Cycle (id_cycle, design_cycle, id_filiere) VALUES(?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cycle.getIdCycle());
        preparedStatement.setString(2, cycle.getDesignCycle());
        preparedStatement.setString(3, cycle.getIdFiliere());

        preparedStatement.executeUpdate();
    }

    public void removeCycle(String id) throws SQLException {
        String sql = "DELETE FROM Cycle WHERE id_cycle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateCycle(Cycle cycle) throws SQLException {
        String sql = "UPDATE Cycle SET design_cycle = ?, id_filiere = ? WHERE id_cycle = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cycle.getDesignCycle());
        preparedStatement.setString(2, cycle.getIdFiliere());
        preparedStatement.setString(3, cycle.getIdCycle());

        preparedStatement.executeUpdate();
    }

    public Cycle getCycle(String id) throws SQLException {
        String sql = "SELECT * FROM Cycle WHERE id_cycle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Cycle(
                    resultSet.getString("id_cycle"),
                    resultSet.getString("design_cycle"),
                    resultSet.getString("id_filiere")
            );
        }
        return null;
    }

    // Semestre methods
    public void addSemestre(Semestre semestre) throws SQLException {
        String sql = "INSERT IGNORE INTO Semestre (id_semestre, num_semestre, id_cycle) VALUES(?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, semestre.getIdSemestre());
        preparedStatement.setInt(2, (int)semestre.getNumSemestre());
        preparedStatement.setString(3, semestre.getIdCycle());

        preparedStatement.executeUpdate();
    }

    public void removeSemestre(String id) throws SQLException {
        String sql = "DELETE FROM Semestre WHERE id_semestre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateSemestre(Semestre semestre) throws SQLException {
        String sql = "UPDATE Semestre SET num_semestre = ?, id_cycle = ? WHERE id_semestre = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, semestre.getNumSemestre());
        preparedStatement.setString(2, semestre.getIdCycle());
        preparedStatement.setString(3, semestre.getIdSemestre());

        preparedStatement.executeUpdate();
    }

    public Semestre getSemestre(String id) throws SQLException {
        String sql = "SELECT * FROM Semestre WHERE id_semestre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Semestre(
                    resultSet.getString("id_semestre"),
                    resultSet.getInt("num_semestre"),
                    resultSet.getString("id_cycle")
            );
        }
        return null;
    }

    // Section methods
    public void addSection(Section section) throws SQLException {
        String sql = "INSERT IGNORE INTO Section (id_section, num_section, id_cycle) VALUES(?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, section.getIdSection());
        preparedStatement.setInt(2, section.getNumSection());
        preparedStatement.setString(3, section.getIdCycle());

        preparedStatement.executeUpdate();
    }

    public void removeSection(String id) throws SQLException {
        String sql = "DELETE FROM Section WHERE id_section = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateSection(Section section) throws SQLException {
        String sql = "UPDATE Section SET num_section = ?, id_cycle = ? WHERE id_section = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, section.getNumSection());
        preparedStatement.setString(2, section.getIdCycle());
        preparedStatement.setString(3, section.getIdSection());

        preparedStatement.executeUpdate();
    }

    public Section getSection(String id) throws SQLException {
        String sql = "SELECT * FROM Section WHERE id_section = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Section(
                    resultSet.getString("id_section"),
                    resultSet.getInt("num_section"),
                    resultSet.getString("id_cycle")
            );
        }
        return null;
    }

    // Groupe methods
    public void addGroupe(Groupe groupe) throws SQLException {
        String sql = "INSERT IGNORE INTO Groupe (id_groupe, num_groupe, id_section, id_semestre_pair, id_semestre_impair) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, groupe.getIdGroupe());
        preparedStatement.setInt(2, (int)groupe.getNumGroupe());
        preparedStatement.setString(3, groupe.getIdSection());
        preparedStatement.setString(4, groupe.getIdSemestrePair());
        preparedStatement.setString(5, groupe.getIdSemestreImpair());

        preparedStatement.executeUpdate();
    }

    public void removeGroupe(String id) throws SQLException {
        String sql = "DELETE FROM Groupe WHERE id_groupe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateGroupe(Groupe groupe) throws SQLException {
        String sql = "UPDATE Groupe SET num_groupe = ?, id_section = ?, id_semestre_pair = ?, id_semestre_impair = ? WHERE id_groupe = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, (int)groupe.getNumGroupe());
        preparedStatement.setString(2, groupe.getIdSection());
        preparedStatement.setString(3, groupe.getIdSemestrePair());
        preparedStatement.setString(4, groupe.getIdSemestreImpair());
        preparedStatement.setString(5, groupe.getIdGroupe());

        preparedStatement.executeUpdate();
    }

    public Groupe getGroupe(String id) throws SQLException {
        String sql = "SELECT * FROM Groupe WHERE id_groupe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Groupe(
                    resultSet.getString("id_groupe"),
                    resultSet.getInt("num_groupe"),
                    resultSet.getString("id_section"),
                    resultSet.getString("id_semestre_pair"),
                    resultSet.getString("id_semestre_impair")
            );
        }
        return null;
    }

    // Conge methods
    public void addConge(Conge conge) throws SQLException {
        if (conge.getEtudiant() == null) {
            throw new SQLException("Cannot add conge without student reference");
        }

        // Check if student is already reintegrated
        if (isStudentReintegrated(conge.getEtudiant().getIdEtu())) {
            throw new SQLException("Student has already been reintegrated and cannot submit new requests");
        }

        // Generate conge ID (X-AA-00000000 format)
        String idPrefix = "C" + (conge.getDateDemande().getYear() - 100) + conge.getEtudiant().getIdEtu();
        String sqlCount = "SELECT COUNT(*) FROM Conge WHERE id_demande LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sqlCount);
        ps.setString(1, idPrefix + "%");
        ResultSet rs = ps.executeQuery();
        rs.next();
        String id = idPrefix + String.format("%06d", rs.getInt(1) + 1);

        String sql = "INSERT IGNORE INTO Conge (id_demande, id_etu, date_demande, duree, etat, justificatif) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setLong(2, conge.getEtudiant().getIdEtu());
            stmt.setDate(3, conge.getDateDemande());
            stmt.setInt(4, (int)conge.getDuree());
            stmt.setString(5, conge.getEtat().toString());

            if (conge.getJustificatif() != null) {
                try {
                    stmt.setBlob(6, new FileInputStream(conge.getJustificatif()));
                } catch (FileNotFoundException e) {
                    stmt.setNull(6, Types.BLOB);
                }
            } else {
                stmt.setNull(6, Types.BLOB);
            }

            stmt.executeUpdate();
            conge.setIdDemande(id);
        }
    }

    public void removeConge(String id) throws SQLException {
        // First delete related action_admin records
        String deleteActionsSql = "DELETE FROM Action_admin WHERE id_conge = ?";
        PreparedStatement actionStmt = connection.prepareStatement(deleteActionsSql);
        actionStmt.setString(1, id);
        actionStmt.executeUpdate();

        // Then delete the conge record
        String sql = "DELETE FROM Conge WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateConge(Conge conge) throws SQLException {
        String sql = "UPDATE Conge SET date_demande = ?, duree = ?, etat = ?, justificatif = ? WHERE id_demande = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, conge.getDateDemande());
            stmt.setInt(2, (int)conge.getDuree());
            stmt.setString(3, conge.getEtat().toString());

            if (conge.getJustificatif() != null) {
                try {
                    stmt.setBlob(4, new FileInputStream(conge.getJustificatif()));
                } catch (FileNotFoundException e) {
                    stmt.setNull(4, Types.BLOB);
                }
            } else {
                stmt.setNull(4, Types.BLOB);
            }

            stmt.setString(5, conge.getIdDemande());
            stmt.executeUpdate();
        }
    }

    public void updateCongeEtat(String idDemande, EtatTraitement etat) throws SQLException {
        String sql = "UPDATE Conge SET etat = ? WHERE id_demande = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, etat.toString());
            pstmt.setString(2, idDemande);
            pstmt.executeUpdate();
        }
    }

    public Conge getConge(String id) throws SQLException {
        String sql = "SELECT * FROM Conge WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Conge conge = new Conge(
                    resultSet.getString("id_demande"),
                    resultSet.getDate("date_demande"),
                    resultSet.getInt("duree"),
                    EtatTraitement.valueOf(resultSet.getString("etat").replaceAll(" ", "").toUpperCase()),
                    resultSet.getBlob("justificatif") != null ?
                            resultSet.getBlob("justificatif").getBinaryStream() : null
            );
            String typeStr = resultSet.getString("type");
            if (typeStr != null) {
                for (TypeConge type : TypeConge.values()) {
                    if (type.toString().equals(typeStr)) {
                        conge.setType(type);
                        break;
                    }
                }
            }
            return conge;
        }
        return null;
    }

    public List<Conge> getAllConges() throws SQLException {
        String sql = "SELECT * FROM Conge";
        List<Conge> conges = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            conges.add(new Conge(
                    resultSet.getString("id_demande"),
                    resultSet.getDate("date_demande"),
                    resultSet.getInt("duree"),
                    EtatTraitement.valueOf(resultSet.getString("etat").replaceAll(" ", "").toUpperCase()),
                    (FileInputStream) resultSet.getBlob("justificatif")
            ));
        }

        return conges;
    }

    public List<Conge> getAllCongesWithStudents() throws SQLException {
        String sql = "SELECT c.*, e.* FROM Conge c JOIN Etudiant e ON c.id_etu = e.id_etu ORDER BY c.date_demande DESC";
        List<Conge> conges = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                        rs.getLong("e.id_etu"),
                        rs.getString("e.nom"),
                        rs.getString("e.prenom"),
                        rs.getDate("e.date_naiss"),
                        rs.getString("e.id_groupe"),
                        rs.getString("e.email_etu")
                );

                Conge conge = new Conge(
                        rs.getString("c.id_demande"),
                        rs.getDate("c.date_demande"),
                        rs.getInt("c.duree"),
                        EtatTraitement.valueOf(rs.getString("c.etat").replaceAll(" ", "").toUpperCase()),
                        rs.getBlob("c.justificatif") != null ?
                                rs.getBlob("c.justificatif").getBinaryStream() : null
                );
                conge.setEtudiant(etudiant);
                String typeStr = rs.getString("c.type");
                if (typeStr != null) {
                    for (TypeConge type : TypeConge.values()) {
                        if (type.toString().equals(typeStr)) {
                            conge.setType(type);
                            break;
                        }
                    }
                }
                conges.add(conge);
            }
        }
        return conges;
    }

    public InputStream getJustificationFile(String demandeId) throws SQLException {
        String sql = "SELECT justificatif FROM Conge WHERE id_demande = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, demandeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBinaryStream("justificatif");
            }
        }
        return null;
    }

    // DemReins methods
    public void addDemReins(DemReins demReins, char type) throws SQLException {
        // Generate demand ID (X-X-00000000 format)
        // type is to tell if the demand is for conge or abandont
        String id = "R" + type + demReins.getIdEtu();


        String sql = "INSERT IGNORE INTO Dem_reins (id_demande, id_etu, date_demande, justificatif, etat) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.setLong(2, demReins.getIdEtu());
        preparedStatement.setDate(3, demReins.getDateDemande());
        preparedStatement.setBlob(4, demReins.getJustificatif());
        preparedStatement.setString(5, demReins.getEtat().toString());

        preparedStatement.executeUpdate();
        demReins.setIdDemande(id);
    }

    public void removeDemReins(String id) throws SQLException {
        String sql = "DELETE FROM Dem_reins WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    public void updateDemReins(DemReins demReins) throws SQLException {
        String sql = "UPDATE Dem_reins SET date_demande = ?, justificatif = ?, etat = ? WHERE id_demande = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, demReins.getDateDemande());
        preparedStatement.setBlob(2, demReins.getJustificatif());
        preparedStatement.setString(3, demReins.getEtat().toString());
        preparedStatement.setString(4, demReins.getIdDemande());

        preparedStatement.executeUpdate();
    }

    public DemReins getDemReins(String id) throws SQLException {
        String sql = "SELECT * FROM Dem_reins WHERE id_demande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new DemReins(
                    resultSet.getString("id_demande"),
                    resultSet.getLong("id_etu"),
                    resultSet.getDate("date_demande"),
                    (FileInputStream) resultSet.getBlob("justificatif"),
                    EtatTraitement.valueOf(resultSet.getString("etat").replaceAll(" ", "").toUpperCase())
            );
        }
        return null;
    }

    // Abondant methods
    public void addAbondant(Abondant abondant) throws SQLException {
        String sql = "INSERT IGNORE INTO Abondant (id_etu, id_admin, date_dec) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, abondant.getIdEtu());
        preparedStatement.setString(2, abondant.getIdAdmin());
        preparedStatement.setDate(3, new java.sql.Date(abondant.getDateDec().getTime()));

        preparedStatement.executeUpdate();
    }

    public void removeAbondant(long etudiantId) throws SQLException {
        String sql = "DELETE FROM Abondant WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, etudiantId);
        preparedStatement.executeUpdate();
    }

    public void updateAbondant(Abondant abondant) throws SQLException {
        String sql = "UPDATE Abondant SET id_admin = ?, date_dec = ? WHERE id_etu = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, abondant.getIdAdmin());
        preparedStatement.setDate(2, new java.sql.Date(abondant.getDateDec().getTime()));
        preparedStatement.setLong(3, abondant.getIdEtu());

        preparedStatement.executeUpdate();
    }

    public Abondant getAbondant(long etudiantId) throws SQLException {
        String sql = "SELECT * FROM Abondant WHERE id_etu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, etudiantId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Abondant(
                    resultSet.getLong("id_etu"),
                    resultSet.getString("id_admin"),
                    resultSet.getDate("date_dec")
            );
        }
        return null;
    }

    public List<Abondant> getAbondant() throws SQLException {
        String sql = "SELECT * FROM Abondant ORDER BY date_dec DESC";
        List<Abondant> abondants = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                abondants.add(new Abondant(
                        rs.getLong("id_etu"),
                        rs.getString("id_admin"),
                        rs.getDate("date_dec")
                ));
            }
        }
        return abondants;
    }

    // ActionAdmin methods
    public void addActionAdmin(ActionAdmin actionAdmin) throws SQLException {
        String sql = "INSERT IGNORE INTO Action_admin (id_admin, action, temps_action, id_conge, id_reins, pk_abond) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, actionAdmin.getIdAdmin());
        preparedStatement.setString(2, actionAdmin.getAction());
        preparedStatement.setTimestamp(3, actionAdmin.getTempsAction());
        preparedStatement.setString(4, actionAdmin.getIdConge());
        preparedStatement.setString(5, actionAdmin.getIdReins());
        preparedStatement.setLong(6, actionAdmin.getPkAbond());

        preparedStatement.executeUpdate();
    }

    public List<ActionAdmin> getAllActionAdmin(String idAdmin) throws SQLException {
        String sql = "SELECT * FROM Action_admin WHERE id_admin = ?";
        List<ActionAdmin> actions = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, idAdmin);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            actions.add(new ActionAdmin(
                    resultSet.getInt("id_action"),
                    resultSet.getString("id_admin"),
                    resultSet.getString("action"),
                    resultSet.getTimestamp("temps_action"),
                    resultSet.getString("id_conge"),
                    resultSet.getString("id_reins"),
                    resultSet.getLong("pk_abond")
            ));
        }
        return actions;
    }

    // Authentication methods
    public Admin getAdminByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getString("id_admin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        RoleAdmin.valueOf(rs.getString("roles").toUpperCase().replace("ADMIN_", "ADMIN")),
                        rs.getString("email"),
                        null
                );
            }
            return null;
        }
    }

    public Conge getCongeByEtudiant(long idEtu) throws SQLException {
        String sql = "SELECT c.*, e.* FROM Conge c " +
                "JOIN Etudiant e ON c.id_etu = e.id_etu " +
                "WHERE c.id_etu = ? " +
                "ORDER BY c.date_demande DESC " +
                "LIMIT 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, idEtu);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Etudiant etudiant = new Etudiant(
                    resultSet.getLong("id_etu"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getDate("date_naiss"),
                    resultSet.getString("id_groupe"),
                    resultSet.getString("email_etu")
            );

            String etatStr = resultSet.getString("etat");
            EtatTraitement etat = EtatTraitement.fromDisplayName(etatStr);

            Conge conge = new Conge(
                    resultSet.getString("id_demande"),
                    resultSet.getDate("date_demande"),
                    resultSet.getInt("duree"),
                    etat,
                    resultSet.getBinaryStream("justificatif")
            );
            conge.setEtudiant(etudiant);
            
            String typeStr = resultSet.getString("type");
            if (typeStr != null) {
                for (TypeConge type : TypeConge.values()) {
                    if (type.toString().equals(typeStr)) {
                        conge.setType(type);
                        break;
                    }
                }
            }

            return conge;
        }

        return null;
    }

    // Methods for handling reintegrated students
    public void addReintegratedStudent(long idEtu, String originalCongeId) throws SQLException {
        String sql = "INSERT INTO Reintegrated_Students (id_etu, reintegration_date, original_conge_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEtu);
            stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(3, originalCongeId);
            stmt.executeUpdate();
        }
    }

    public boolean isStudentReintegrated(long idEtu) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Reintegrated_Students WHERE id_etu = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEtu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public java.sql.Connection getConnection() {
        return connection;
    }

    public void deleteAbandonment(long idEtu) throws SQLException {
        String sql = "DELETE FROM Abondant WHERE id_etu = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEtu);
            stmt.executeUpdate();
        }
    }

    public int getTotalStudents() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Etudiant";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getTotalAbondants() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Abondant";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}