INSERT INTO Filiere (design_filiere) VALUES
('Informatique'),
('Mathématiques'),
('Physique');

INSERT INTO Cycle (design_cycle, id_filiere) VALUES
('Licence', 1),
('Master', 1),
('Licence', 2),
('Licence', 3);

INSERT INTO Semestre (num_semestre) VALUES
(1), (2), (3), (4), (5), (6);

INSERT INTO Section (num_section, id_cycle) VALUES
(1, 1), (2, 1), (1, 2), (1, 3);

INSERT INTO Groupe (num_groupe, id_section, id_semestre) VALUES
(1, 1, 1),
(2, 1, 1),
(1, 2, 3),
(1, 3, 2);

INSERT INTO Etudiant (id_etu, nom, prenom, date_naiss, id_groupe) VALUES
(1001, 'Mhamedi', 'Yasser', '2005-11-23', 2),
(1002, 'Martin', 'Sophie', '1999-05-22', 2),
(1003, 'Bernard', 'Pierre', '2001-03-10', 1),
(1004, 'Petit', 'Marie', '2000-11-30', 3);

INSERT INTO Module (id_module, nom_module, id_semestre) VALUES
('MATH101', 'Algèbre linéaire', 1),
('INFO201', 'Base de données', 3),
('PHYS301', 'Mécanique quantique', 5);

INSERT INTO admin (id_admin, nom, prenom, roles, email, mot_passe)
VALUES (1, "Mhamed", "Yasser", "admin_conge", "ss", "ss");