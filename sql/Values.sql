-- Filiere (XXX-0)
INSERT INTO Filiere VALUES
('INF-1', 'Informatique'),
('GES-1', 'Gestion'),
('RES-1', 'Réseaux');

-- Cycle (AAA)
INSERT INTO Cycle VALUES
('LIC', 'Licence Informatique', 'INF-1'),
('LGE', 'Licence Gestion', 'GES-1'),
('MAS', 'Master Informatique', 'INF-1');

-- Semestre (CYCLE-NUM)
INSERT INTO Semestre VALUES
('LIC-1', 1, 'LIC'),
('LIC-2', 2, 'LIC'),
('LGE-1', 1, 'LGE'),
('MAS-1', 1, 'MAS');

-- Section (CYCLE-S-NUM)
INSERT INTO Section VALUES
('LIC-S1', 1, 'LIC'),
('LIC-S2', 2, 'LIC'),
('LGE-S1', 1, 'LGE');

-- Groupe (SECTION-G-NUM)

INSERT INTO Groupe VALUES
('LIC-S1-G1', 1, 'LIC-S1', 'LIC-1', 'LIC-2'),
('LIC-S1-G2', 2, 'LIC-S1', 'LIC-1', 'LIC-2'),
('LGE-S1-G1', 1, 'LGE-S1', 'LGE-1', 'LGE-1');

-- Etudiant (8-digit)
INSERT INTO Etudiant VALUES
(32011001, 'Dupont', 'Jean', '2000-05-15', 'LIC-S1-G1','yassermhamedi333@gmail.com'),
(32011004, 'Eggy1', 'Eggy', '2000-05-15', 'LIC-S1-G1','yassermhamedi333@gmail.com'),
(32011005, 'Eggy2', 'Jean', '2000-05-15', 'LIC-S1-G1','yassermhamedi333@gmail.com'),
(32011006, 'Eggy3', 'Jean', '2000-05-15', 'LIC-S1-G1','yassermhamedi333@gmail.com'),
(32011002, 'Martin', 'Sophie', '2001-08-22', 'LIC-S1-G1','hichamhichamhichamou@gmail.com'),
(32011003, 'Bernard', 'Pierre', '2000-11-30', 'LIC-S1-G2','hichamhichamhichamou@gmail.com');

-- Admin (X-X-000)
INSERT INTO Admin VALUES
('TC001', 'Ahmed', 'Mohsin', 'admin_conge_abandont', 'a', '$2a$10$coOsPNKaVhCYniC41qlw1uuuZThJHwSDWMxtzaZHLPRtHZRMFR.Bm'),
('RC001', 'Mhamed', 'Yasser', 'admin_comptes', 'b', '$2a$10$nQvz9t0wrhgUH7wPSr7tI.C/ErCuZ4hRmstKwBXCJkH0vYh5Bgdly'),
('TA001', 'za', 'za', 'admin_conge_abandont', 'c', '$2a$10$RGPyNRVeQxXv3IwCwdQt4eo/3O5vkC99XgMzMjuzrXdhRzIkuXtXK');

-- Conge (TYPE-ANNEE-MATRICULE)
INSERT INTO Conge VALUES
('C-25-32011001', 32011001, '2025-01-10', 'Maladie longue durée', 30, 'Accepté', 'Justificatif1');

-- Abondant
INSERT INTO Abondant VALUES
(32011001, 'TA001', '2025-03-01'),
(32011004, 'TA001', '2025-03-01'),
(32011005, 'TA001', '2025-03-01'),
(32011003, 'TA001', '2025-03-01');

-- Action_admin
INSERT INTO Action_admin (id_admin, action, temps_action, id_conge, id_reins, pk_abond)
VALUES ('TA001', 'Validation congé', NOW(), 'C-25-32011001', NULL, NULL);