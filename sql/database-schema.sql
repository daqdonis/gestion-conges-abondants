-- 1. Create the database
DROP DATABASE IF EXISTS conges_abondant;
CREATE DATABASE conges_abondant;
USE conges_abondant;

-- 2. Create tables with proper relationships

-- Filiere table
CREATE TABLE IF NOT EXISTS Filiere(
    id_filiere INT NOT NULL AUTO_INCREMENT,
    design_filiere VARCHAR(63) NOT NULL,
    PRIMARY KEY (id_filiere)
);

-- Cycle table
CREATE TABLE IF NOT EXISTS Cycle(
    id_cycle INT NOT NULL AUTO_INCREMENT,
    design_cycle VARCHAR(63) NOT NULL,
    id_filiere INT NOT NULL,
    PRIMARY KEY (id_cycle),
    FOREIGN KEY (id_filiere) REFERENCES Filiere(id_filiere)
);

-- Semestre table
CREATE TABLE IF NOT EXISTS Semestre(
    id_semestre INT NOT NULL AUTO_INCREMENT,
    num_semestre INT NOT NULL,
    PRIMARY KEY (id_semestre)
);

-- Section table
CREATE TABLE IF NOT EXISTS Section(
    id_section INT NOT NULL AUTO_INCREMENT,
    num_section INT NOT NULL,
    id_cycle INT NOT NULL,
    PRIMARY KEY (id_section),
    FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
);

-- Groupe table
CREATE TABLE IF NOT EXISTS Groupe(
    id_groupe INT NOT NULL AUTO_INCREMENT,
    num_groupe INT NOT NULL,
    id_section INT NOT NULL,
    id_semestre INT NOT NULL,
    PRIMARY KEY (id_groupe),
    FOREIGN KEY (id_section) REFERENCES Section(id_section),
    FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre)
);

-- Etudiant table (simplified without id_conge and id_dem_reins)
CREATE TABLE IF NOT EXISTS Etudiant(
    id_etu INT NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    date_naiss DATE NOT NULL,
    id_groupe INT NOT NULL,
    PRIMARY KEY (id_etu),
    FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe)
);

-- Conge table with reference to Etudiant
CREATE TABLE IF NOT EXISTS Conge(
    id_demande INT NOT NULL AUTO_INCREMENT,
    id_etu INT NOT NULL,  -- References student
    date_demande DATE NOT NULL,
    duree INT,
    etat ENUM('En attente','Refusé','Accepté') NOT NULL DEFAULT 'En attente',
    justificatif MEDIUMBLOB NOT NULL,
    PRIMARY KEY (id_demande),
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
);

-- Dem_reins table with reference to Etudiant
CREATE TABLE IF NOT EXISTS Dem_reins(
    id_demande INT NOT NULL AUTO_INCREMENT,
    id_etu INT NOT NULL,  -- References student
    date_demande DATE NOT NULL,
    justificatif MEDIUMBLOB NOT NULL,
    etat ENUM('En attente','Refusé','Accepté') NOT NULL DEFAULT 'En attente',
    PRIMARY KEY (id_demande),
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
);

-- Module table
CREATE TABLE IF NOT EXISTS Module(
    id_module VARCHAR(31) NOT NULL,
    nom_module VARCHAR(31) NOT NULL,
    id_semestre INT NOT NULL,
    PRIMARY KEY (id_module),
    FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre)
);

-- Note_module table
CREATE TABLE IF NOT EXISTS Note_module(
    id_etu INT NOT NULL,
    id_module VARCHAR(31) NOT NULL,
    note_td FLOAT,
    note_tp FLOAT,
    note_exam FLOAT,
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
    FOREIGN KEY (id_module) REFERENCES Module(id_module)
);

-- Admin table
CREATE TABLE IF NOT EXISTS Admin(
    id_admin INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    roles SET('admin_conge', 'admin_abondant', 'admin_comptes') NOT NULL,
    email VARCHAR(63) NOT NULL,
    mot_passe VARCHAR(63) NOT NULL,
    PRIMARY KEY (id_admin)
);

-- Abondant table
CREATE TABLE IF NOT EXISTS Abondant(
    id_etu INT NOT NULL,
    id_admin INT NOT NULL,
    date_dec DATE NOT NULL,
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
    FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
    CONSTRAINT pk_abond PRIMARY KEY (id_etu)
);

-- Action_admin table
CREATE TABLE IF NOT EXISTS Action_admin(
    id_action INT NOT NULL AUTO_INCREMENT,
    id_admin INT NOT NULL,
    action TINYTEXT NOT NULL,
    temps_action TIMESTAMP NOT NULL,
    id_conge INT,
    id_reins INT,
    pk_abond INT,
    PRIMARY KEY (id_action),
    FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
    FOREIGN KEY (id_conge) REFERENCES Conge(id_demande),
    FOREIGN KEY (id_reins) REFERENCES Dem_reins(id_demande),
    FOREIGN KEY (pk_abond) REFERENCES Abondant(id_etu)
);

-----
-- new database creation script
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
    id_semestre VARCHAR(10) PRIMARY KEY,
    num_semestre INT NOT NULL,
    id_cycle VARCHAR(4) NOT NULL,
    FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Section (S-NUM)
CREATE TABLE Section(
    id_section VARCHAR(10) PRIMARY KEY,
    num_section INT NOT NULL,
    id_cycle VARCHAR(4) NOT NULL,
    FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Groupe (G-NUM)
CREATE TABLE Groupe(
    id_groupe VARCHAR(10) PRIMARY KEY,
    num_groupe INT NOT NULL,
    id_section VARCHAR(10) NOT NULL,
    id_semestre VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_section) REFERENCES Section(id_section),
    FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Etudiant (8-digit)
CREATE TABLE Etudiant(
    id_etu VARCHAR(8) PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    date_naiss DATE NOT NULL,
    id_groupe VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Conge (X-AA-00000000)
CREATE TABLE Conge(
    id_demande VARCHAR(15) PRIMARY KEY,
    id_etu VARCHAR(8) NOT NULL,
    date_demande DATE NOT NULL,
    duree INT,
    etat ENUM('En attente','Refusé','Accepté') DEFAULT 'En attente',
    justificatif MEDIUMBLOB NOT NULL,
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dem_reins (X-AA-00000000)
CREATE TABLE Dem_reins(
    id_demande VARCHAR(15) PRIMARY KEY,
    id_etu VARCHAR(8) NOT NULL,
    date_demande DATE NOT NULL,
    justificatif MEDIUMBLOB NOT NULL,
    etat ENUM('En attente','Refusé','Accepté') DEFAULT 'En attente',
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Module (XXX-0-X0-XXX)
CREATE TABLE Module(
    id_module VARCHAR(31) PRIMARY KEY,
    nom_module VARCHAR(31) NOT NULL,
    id_semestre VARCHAR(10) NOT NULL,
    id_filiere VARCHAR(10) NOT NULL,
    niveau_etude VARCHAR(3) NOT NULL,
    FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre),
    FOREIGN KEY (id_filiere) REFERENCES Filiere(id_filiere)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Note_module
CREATE TABLE Note_module(
    id_etu VARCHAR(8) NOT NULL,
    id_module VARCHAR(31) NOT NULL,
    note_td FLOAT,
    note_tp FLOAT,
    note_exam FLOAT,
    PRIMARY KEY (id_etu, id_module),
    FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
    FOREIGN KEY (id_module) REFERENCES Module(id_module)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Admin (X-X-000)
CREATE TABLE Admin(
    id_admin VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    roles SET('admin_conge','admin_abondant','admin_comptes') NOT NULL,
    email VARCHAR(63) NOT NULL,
    mot_passe VARCHAR(63) NOT NULL,
    type_compte CHAR(1) NOT NULL,
    type_demande_traite CHAR(1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Abondant
CREATE TABLE Abondant(
    id_etu VARCHAR(8) PRIMARY KEY,
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
    pk_abond VARCHAR(8),
    FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
    FOREIGN KEY (id_conge) REFERENCES Conge(id_demande),
    FOREIGN KEY (id_reins) REFERENCES Dem_reins(id_demande),
    FOREIGN KEY (pk_abond) REFERENCES Abondant(id_etu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;