
-- -------------------------------
-- SCRIPT COMPLET WALIBIX V1
-- -------------------------------

-- 1. Création de la base
DROP DATABASE IF EXISTS Walibix;
CREATE DATABASE Walibix;
USE Walibix;

-- 2. Création des tables
CREATE TABLE OffreReduction (
    offre_reduc_id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    description TEXT,
    pourcentage DECIMAL(5,2),
    condition_age_min INT,
    condition_age_max INT
);

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(100) NOT NULL,
    admin BOOLEAN DEFAULT FALSE,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    date_naissance DATE,
    type_client ENUM('Invite', 'Membre'),
    offre_reduc_id INT NULL,
    FOREIGN KEY (offre_reduc_id) REFERENCES OffreReduction(offre_reduc_id),
    CHECK (type_client IN ('Invite', 'Membre'))
);

CREATE TABLE Attraction (
    attraction_id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    type_attrac VARCHAR(50),
    description TEXT,
    prix_base DECIMAL(7,2),
    image VARCHAR(255)
);

CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    heure TIME,
    user_id INT,
    attraction_id INT,
    prix_total DECIMAL(7,2),
    prix_avec_reduc DECIMAL(7,2),
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (attraction_id) REFERENCES Attraction(attraction_id)
);

-- 3. Insertion des attractions
INSERT INTO Attraction (nom, type_attrac, description, prix_base, image) VALUES
('Attraction Supprimé',  'archive', 'Attraction supprimée automatiquement', 0.0, ''),
('Pulsar', 'Water Coaster', 'Un splash gigantesque propulsé en avant et en arrière sur l’eau.', 32.00, 'pulsar.jpg'),
('Vampire', 'Roller Coaster', 'Un grand huit suspendu où vos pieds pendent dans le vide.', 29.50, 'vampire.jpg'),
('Loup-Garou', 'Wooden Coaster', 'Montagnes russes en bois pour les amateurs de frissons classiques.', 28.00, 'loup_garou.jpg'),
('Dalton Terror', 'Drop Tower', 'Une chute libre de 77 mètres pour les plus courageux.', 27.00, 'dalton_terror.jpg'),
('Challenge of Tutankhamon', 'Dark Ride', 'Aventure interactive dans une pyramide égyptienne mystérieuse.', 25.00, 'tutankhamon.jpg'),
('Psyké Underground', 'Launch Coaster', 'Un looping obscur propulsé à haute vitesse.', 30.00, 'psyke_underground.jpg'),
('Tiki-Waka', 'Family Coaster', 'Parcours amusant et coloré dans un décor polynésien.', 26.50, 'tiki_waka.jpg'),
('Flashback', 'Water Ride', 'Descente aquatique en bateau avec une remontée surprise.', 24.00, 'flashback.jpg');

-- 4. Insertion des offres de réduction
INSERT INTO OffreReduction (nom, description, pourcentage, condition_age_min, condition_age_max) VALUES
('Jeunesse', 'Réduction jeunesse : 20% pour les moins de 18 ans', 20.00, 0, 17),
('Étudiant', 'Réduction étudiants : 10% pour les 18-25 ans', 10.00, 18, 25),
('Senior', 'Réduction senior : 15% pour les plus de 65 ans', 15.00, 65, 130);

-- 5. Insertion des utilisateurs
INSERT INTO User (email, mot_de_passe, admin, nom, prenom, date_naissance, type_client, offre_reduc_id) VALUES
('admin@gmail.com', 'admin123', TRUE, 'Admin', 'Admin', '1980-01-01', 'Membre', NULL),
('paul.dupont@gmail.com', 'paul123', FALSE, 'Dupont', 'Paul', '2005-04-15', 'Membre', 2),
('emma.leroy@gmail.com', 'emma123', FALSE, 'Leroy', 'Emma', '2000-07-22', 'Membre', 2),
('lucas.martin@gmail.com', 'lucas123', FALSE, 'Martin', 'Lucas', '1955-11-30', 'Membre',3),
('sophie.moreau@gmail.com', 'sophie123', FALSE, 'Moreau', 'Sophie', '2010-02-12', 'Invite', NULL),
('julie.marchand@gmail.com', 'julie123', FALSE, 'Marchand', 'Julie', '2012-08-05', 'Invite', NULL),
('nicolas.bernard@gmail.com', 'nico123', FALSE, 'Bernard', 'Nicolas', '1999-12-10', 'Membre', 2),
('marie.duval@gmail.com', 'marie123', FALSE, 'Duval', 'Marie', '1950-06-18', 'Membre', 3),
('thomas.roche@gmail.com', 'thomas123', FALSE, 'Roche', 'Thomas', '1998-09-03', 'Membre', NULL);


-- 6. Insertion des réservations
INSERT INTO Reservation (date, heure, user_id, attraction_id, prix_total, prix_avec_reduc) VALUES
('2024-05-01', '10:30:00', 2, 2, 32.00, 25.60),
('2024-05-01', '11:00:00', 3, 3, 29.50, 26.55),
('2024-05-01', '14:00:00', 4, 4, 28.00, 23.80),
('2024-05-01', '15:30:00', 5, 8, 26.50, 26.50),
('2024-02-03', '10:00:00', 7, 9, 29.00, 23.20),
('2024-04-03', '11:15:00', 8, 3, 23.50, 18.80),
('2024-04-03', '14:45:00', 9, 5, 22.00, 18.70),
('2024-04-03', '16:00:00', 7, 6, 25.00, 22.50),
('2024-04-02', '13:00:00', 6, 5, 27.00, 24.30);
