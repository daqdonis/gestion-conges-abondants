CREATE DATABASE IF NOT EXISTS conges_abondant;


USE conges_abondant;


CREATE TABLE IF NOT EXISTS Filiere(
                                      id_filiere INT NOT NULL AUTO_INCREMENT,
                                      design_filiere VARCHAR(63) NOT NULL,
                                      PRIMARY KEY (id_filiere)
);

CREATE TABLE IF NOT EXISTS Cycle(
                                    id_cycle INT NOT NULL AUTO_INCREMENT,
                                    design_cycle VARCHAR(63) NOT NULL ,
                                    id_filiere INT NOT NULL,
                                    PRIMARY KEY (id_cycle),
                                    FOREIGN KEY (id_filiere) REFERENCES Filiere(id_filiere)
);

CREATE TABLE IF NOT EXISTS Section(
                                      id_section INT NOT NULL AUTO_INCREMENT,
                                      num_section INT NOT NULL,
                                      id_cycle INT NOT NULL,
                                      PRIMARY KEY (id_section),
                                      FOREIGN KEY (id_cycle) REFERENCES Cycle(id_cycle)
);

CREATE TABLE IF NOT EXISTS Semestre(
                                       id_semestre INT NOT NULL AUTO_INCREMENT,
                                       num_semestre INT NOT NULL,
                                       PRIMARY KEY (id_semestre)
);

CREATE TABLE IF NOT EXISTS Groupe(
                                     id_groupe INT NOT NULL AUTO_INCREMENT,
                                     num_groupe INT NOT NULL,
                                     id_section INT NOT NULL,
                                     id_semestre INT NOT NULL,
                                     PRIMARY KEY (id_groupe),
                                     FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre),
                                     FOREIGN KEY (id_section) REFERENCES Section(id_section)
);

CREATE TABLE IF NOT EXISTS Conge(
                                    id_demande INT NOT NULL AUTO_INCREMENT,
                                    date_demande DATE NOT NULL,
                                    duree INT,
                                    etat ENUM('En attente','Refusé','Accepté') NOT NULL DEFAULT 'En attente',
                                    justificatif MEDIUMBLOB NOT NULL,
                                    PRIMARY KEY (id_demande)
);

CREATE TABLE IF NOT EXISTS Dem_reins(
                                        id_demande INT NOT NULL AUTO_INCREMENT,
                                        date_demande DATE NOT NULL,
                                        justificatif MEDIUMBLOB NOT NULL,
                                        etat ENUM('En attente','Refusé','Accepté') NOT NULL DEFAULT 'En attente',
                                        PRIMARY KEY (id_demande)
);


CREATE TABLE IF NOT EXISTS Etudiant(
                                       id_etu INT NOT NULL,
                                       nom VARCHAR(255) NOT NULL,
                                       prenom VARCHAR(255) NOT NULL,
                                       date_naiss DATE NOT NULL,
                                       id_groupe INT NOT NULL,
                                       id_conge INT,
                                       id_dem_reins INT,
                                       PRIMARY KEY (id_etu),
                                       FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe),
                                       FOREIGN KEY (id_conge) REFERENCES Conge(id_demande),
                                       FOREIGN KEY (id_dem_reins) REFERENCES Dem_reins(id_demande)
);

CREATE TABLE IF NOT EXISTS Module(
                                     id_module VARCHAR(31) NOT NULL,
                                     nom_module VARCHAR(31) NOT NULL,
                                     id_semestre INT NOT NULL,
                                     PRIMARY KEY (id_module),
                                     FOREIGN KEY (id_semestre) REFERENCES Semestre(id_semestre)
);

CREATE TABLE IF NOT EXISTS Note_module(
                                          id_etu INT NOT NULL,
                                          id_module VARCHAR(31) NOT NULL,
                                          note_td FLOAT,
                                          note_tp FLOAT,
                                          note_exam FLOAT,
                                          FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
                                          FOREIGN KEY (id_module) REFERENCES Module(id_module)
);

CREATE TABLE IF NOT EXISTS Admin(
                                    id_admin INT NOT NULL AUTO_INCREMENT,
                                    nom VARCHAR(255) NOT NULL,
                                    prenom VARCHAR(255) NOT NULL,
                                    roles SET('admin_conge', 'admin_abondant', 'admin_comptes') NOT NULL,
                                    email VARCHAR(63) NOT NULL,
                                    mot_passe VARCHAR(63) NOT NULL,
                                    PRIMARY KEY (id_admin)
);

CREATE TABLE IF NOT EXISTS Abondant(
                                       id_etu INT NOT NULL,
                                       id_admin INT NOT NULL,
                                       FOREIGN KEY (id_etu) REFERENCES Etudiant(id_etu),
                                       FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
                                       CONSTRAINT pk_abond PRIMARY KEY (id_etu)
);

CREATE TABLE IF NOT EXISTS Action_admin(
                                           id_admin INT NOT NULL,
                                           action TINYTEXT NOT NULL,
                                           temps_action TIMESTAMP NOT NULL,
                                           id_conge INT,
                                           id_reins INT,
                                           pk_abond INT,
                                           FOREIGN KEY (id_admin) REFERENCES Admin(id_admin),
                                           FOREIGN KEY (id_conge) REFERENCES Conge(id_demande),
                                           FOREIGN KEY (id_reins) REFERENCES Dem_reins(id_demande),
                                           FOREIGN KEY (pk_abond) REFERENCES Abondant(id_etu)
);