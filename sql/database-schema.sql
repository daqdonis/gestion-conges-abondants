DROP DATABASE IF EXISTS conges_abondant;
CREATE DATABASE conges_abondant;
USE conges_abondant;

-- Filiere (XXX-0)
CREATE TABLE Filiere(
                        id_filiere VARCHAR(10) PRIMARY KEY,
                        design_filiere VARCHAR(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Cycle (AAA or AAA+number)
CREATE TABLE Cycle(
                      id_cycle VARCHAR(4) PRIMARY KEY,
                      design_cycle VARCHAR(63) NOT NULL,
                      id_filiere VARCHAR(10) NOT NULL,
                      FOREIGN KEY (id_filiere) REFERENCES Filiere(id_filiere)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Semestre (CYCLE-NUM)
CREATE TABLE Semestre(
                         id_semestre VARCHAR(13) PRIMARY KEY,
                         num_semestre INT NOT NULL,
                         id_cycle VARCHAR(4) NOT NULL,
                         FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Section (S-NUM)
CREATE TABLE Section(
                        id_section VARCHAR(12) PRIMARY KEY,
                        num_section INT NOT NULL,
                        id_cycle VARCHAR(4) NOT NULL,
                        FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Groupe (G-NUM)
CREATE TABLE Groupe(
                       id_groupe VARCHAR(15) PRIMARY KEY,
                       num_groupe INT NOT NULL,
                       id_section VARCHAR(12) NOT NULL,
                       id_semestre_pair VARCHAR(13) NOT NULL,
                       id_semestre_impair VARCHAR(13) NOT NULL,
                       FOREIGN KEY (id_section) REFERENCES Section(id_section),
                       FOREIGN KEY (id_semestre_pair) REFERENCES Semestre(id_semestre),
                       FOREIGN KEY (id_semestre_impair) REFERENCES Semestre(id_semestre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Etudiant (8-digit)
CREATE TABLE Etudiant(
                         id_etu BIGINT PRIMARY KEY,
                         nom VARCHAR(255) NOT NULL,
                         prenom VARCHAR(255) NOT NULL,
                         date_naiss DATE NOT NULL,
                         id_groupe VARCHAR(15) NOT NULL,
                         email_etu VARCHAR(35) NOT NULL,
                         FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Conge (X-AA-00000000)
CREATE TABLE Conge(
                      id_demande VARCHAR(15) PRIMARY KEY,
                      id_etu BIGINT NOT NULL,
                      date_demande DATE NOT NULL,
                      type ENUM('Maladie chronique invalidante', 'Maternité', 'Maladie longue durée', 'Service national', 'Obligations familiales', 'Accidents graves') DEFAULT 'Maladie longue durée',
                      duree INT,
                      etat ENUM('En attente','Refusé','Accepté') DEFAULT 'En attente',
                      justificatif MEDIUMBLOB NOT NULL,
                      FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dem_reins (X-AA-00000000)
CREATE TABLE Dem_reins(
                          id_demande VARCHAR(15) PRIMARY KEY,
                          id_etu BIGINT NOT NULL,
                          date_demande DATE NOT NULL,
                          justificatif MEDIUMBLOB NOT NULL,
                          etat ENUM('En attente','Refusé','Accepté') DEFAULT 'En attente',
                          FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Admin (X-X-000)
CREATE TABLE Admin(
                      id_admin VARCHAR(10) PRIMARY KEY,
                      nom VARCHAR(255) NOT NULL,
                      prenom VARCHAR(255) NOT NULL,
                      roles SET('admin_conge_abandont','admin_comptes') NOT NULL,
                      email VARCHAR(63) UNIQUE NOT NULL,
                      mot_passe VARCHAR(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Abondant
CREATE TABLE Abondant(
                         id_etu BIGINT UNIQUE NOT NULL,
                         id_admin VARCHAR(10) NOT NULL,
                         date_dec DATE NOT NULL,
                         FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
                         FOREIGN KEY (id_admin) REFERENCES Admin(id_admin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Action_admin
CREATE TABLE Action_admin(
                             id_action INT AUTO_INCREMENT PRIMARY KEY,
                             id_admin VARCHAR(10) NOT NULL,
                             action TINYTEXT NOT NULL,
                             temps_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             id_conge VARCHAR(15),
                             id_reins VARCHAR(15),
                             pk_abond BIGINT,
                             FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
                             FOREIGN KEY (id_conge) REFERENCES Conge(id_demande),
                             FOREIGN KEY (id_reins) REFERENCES Dem_reins(id_demande),
                             FOREIGN KEY (pk_abond) REFERENCES Abondant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Reintegrated_Students(
    id_etu BIGINT PRIMARY KEY,
    reintegration_date DATE NOT NULL,
    original_conge_id VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;