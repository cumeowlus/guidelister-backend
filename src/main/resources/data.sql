-- Tables
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE guides (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titre VARCHAR(255) NOT NULL,
  description TEXT,
  nb_jour INT,
  mobilite VARCHAR(100),
  saison VARCHAR(100),
  public_cible VARCHAR(200)
);

CREATE TABLE activites (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titre VARCHAR(255) NOT NULL,
  description TEXT,
  categorie VARCHAR(100),
  adresse VARCHAR(255),
  telephone VARCHAR(50),
  horaires VARCHAR(100),
  site_web VARCHAR(255),
  ordre_visite INT,
  nb_jours INT,
  guide_id BIGINT,
  CONSTRAINT fk_activite_guide FOREIGN KEY (guide_id) REFERENCES guides(id) ON DELETE SET NULL
);

CREATE TABLE guide_user (
  guide_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (guide_id, user_id),
  CONSTRAINT fk_gu_guide FOREIGN KEY (guide_id) REFERENCES guides(id) ON DELETE CASCADE,
  CONSTRAINT fk_gu_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Sample users
INSERT INTO users (email, password, is_admin) VALUES
('alice@example.com', 'password1', TRUE),
('bob@example.com',   'password2', FALSE),
('carole@example.com','password3', FALSE);

-- Sample guides
INSERT INTO guides (titre, description, nb_jour, mobilite, saison, public_cible) VALUES
('Weekend à Paris', 'Un court séjour pour découvrir les classiques de Paris.', 2, 'À pied / Métro', 'Printemps/Été', 'Couples, jeunes adultes'),
('Randonnée Montagne', 'Itinéraire de 5 jours en montagne, niveau intermédiaire.', 5, 'Randonnée', 'Été', 'Randonneurs expérimentés'),
('City trip culturel', 'Musées et monuments en 3 jours', 3, 'Transport public', 'Toute l''année', 'Familles, retraités');

-- Sample activities for guide 1 (Weekend à Paris)
INSERT INTO activites (titre, description, categorie, adresse, telephone, horaires, site_web, ordre_visite, nb_jours, guide_id) VALUES
('Cathédrale Notre-Dame', 'Visite extérieure et quartier historique.', 'Patrimoine', 'Île de la Cité, Paris', NULL, 'Toute la journée', 'https://www.notredamedeparis.fr', 1, 0, 1),
('Musée du Louvre', 'Collection permanente + expositions temporaires.', 'Musée', 'Rue de Rivoli, Paris', '+33 1 40 20 50 50', '09:00-18:00', 'https://www.louvre.fr', 2, 0, 1),
('Balade Seine', 'Croisière et balade le long de la Seine.', 'Loisir', 'Quais de Seine', NULL, 'Selon horaire', NULL, 3, 0, 1);

-- Activities for guide 2 (Randonnée Montagne)
INSERT INTO activites (titre, description, categorie, adresse, telephone, horaires, site_web, ordre_visite, nb_jours, guide_id) VALUES
('Refuge du Col', 'Arrivée au refuge, repas et nuit.', 'Hébergement', 'Massif Central - Col X', NULL, 'Arrivée après-midi', NULL, 1, 1, 2),
('Sommet et panorama', 'Ascension du sommet et vue panoramique.', 'Randonnée', 'Sentier principal', NULL, 'Matin', NULL, 2, 1, 2),
('Lac alpin', 'Baignade et pause pique-nique.', 'Nature', 'Lac Y', NULL, 'Après-midi', NULL, 3, 0, 2);

-- Activities for guide 3 (City trip culturel)
INSERT INTO activites (titre, description, categorie, adresse, telephone, horaires, site_web, ordre_visite, nb_jours, guide_id) VALUES
('Musée d''Art Moderne', 'Collections du 20ème siècle.', 'Musée', 'Rue Z', NULL, '10:00-17:00', NULL, 1, 1, 3),
('Théâtre historique', 'Spectacle local en soirée.', 'Spectacle', 'Place A', NULL, '20:00', NULL, 2, 0, 3);

-- Link users to guides (permissions)
INSERT INTO guide_user (guide_id, user_id) VALUES
(1, 1), -- Alice admin on guide 1
(1, 2), -- Bob has access to guide 1
(2, 1), -- Alice admin on guide 2
(3, 3); -- Carole has access to guide 3
