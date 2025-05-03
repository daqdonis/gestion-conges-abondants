-- Insert Filiere
INSERT INTO Filiere (design_filiere) VALUES ('Informatique'), ('Gestion'), ('Réseaux');

-- Insert Cycle
INSERT INTO Cycle (design_cycle, id_filiere) VALUES 
('Licence', 1), 
('Master', 1), 
('Licence', 2);

-- Insert Semestre
INSERT INTO Semestre (num_semestre) VALUES (1), (2), (3), (4), (5), (6);

-- Insert Section
INSERT INTO Section (num_section, id_cycle) VALUES (1, 1), (2, 1), (1, 2);

-- Insert Groupe
INSERT INTO Groupe (num_groupe, id_section, id_semestre) VALUES 
(1, 1, 1), 
(2, 1, 1), 
(1, 2, 3);

-- Insert Etudiant (no demand references needed now)
INSERT INTO Etudiant (id_etu, nom, prenom, date_naiss, id_groupe) VALUES 
(12345, 'Mhamedi', 'Yasser', '2005-11-23', 1),
(12346, 'Smith', 'John', '2004-05-15', 2);

INSERT INTO admin (id_admin, nom, prenom, roles, email, mot_passe)
VALUES (1, "Mhamed", "Yasser", "admin_conge", "aa", "aa");