-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 23 avr. 2024 à 10:31
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `amphitryon`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorieplat`
--

DROP TABLE IF EXISTS `categorieplat`;
CREATE TABLE IF NOT EXISTS `categorieplat` (
  `IDCATEG` int NOT NULL AUTO_INCREMENT,
  `NOMCATEG` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDCATEG`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorieplat`
--

INSERT INTO `categorieplat` (`IDCATEG`, `NOMCATEG`) VALUES
(1, 'plat_principal'),
(2, 'entree'),
(3, 'dessert');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `IDCOMMANDE` int NOT NULL AUTO_INCREMENT,
  `DATE_SERVICE` date NOT NULL,
  `NUMTABLE` int NOT NULL,
  `IDSERVICE` int NOT NULL,
  `HEURECOMMANDE` time DEFAULT NULL,
  `ETATCOMMANDE` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDCOMMANDE`),
  KEY `FK_COMMANDE_TABLE_RESTO` (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`IDCOMMANDE`, `DATE_SERVICE`, `NUMTABLE`, `IDSERVICE`, `HEURECOMMANDE`, `ETATCOMMANDE`) VALUES
(1, '2024-01-26', 1, 1, '13:27:48', 'non réglée');

-- --------------------------------------------------------

--
-- Structure de la table `commander`
--

DROP TABLE IF EXISTS `commander`;
CREATE TABLE IF NOT EXISTS `commander` (
  `IDPLAT` int NOT NULL,
  `IDCOMMANDE` int NOT NULL,
  `ETATPLAT` char(32) DEFAULT NULL,
  `INFOSCOMPLEMENTAIRES` char(32) DEFAULT NULL,
  `QUANTITE` int DEFAULT NULL,
  PRIMARY KEY (`IDPLAT`,`IDCOMMANDE`),
  KEY `FK_COMMANDER_COMMANDE` (`IDCOMMANDE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commander`
--

INSERT INTO `commander` (`IDPLAT`, `IDCOMMANDE`, `ETATPLAT`, `INFOSCOMPLEMENTAIRES`, `QUANTITE`) VALUES
(1, 1, 'commandé', 'il est pas vegan', 2);

-- --------------------------------------------------------

--
-- Structure de la table `date_service`
--

DROP TABLE IF EXISTS `date_service`;
CREATE TABLE IF NOT EXISTS `date_service` (
  `DATE_SERVICE` date NOT NULL,
  PRIMARY KEY (`DATE_SERVICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `date_service`
--

INSERT INTO `date_service` (`DATE_SERVICE`) VALUES
('2024-01-26'),
('2024-02-14'),
('2024-04-21'),
('2024-04-22'),
('2024-04-23'),
('2024-04-24'),
('2024-04-25'),
('2024-04-26'),
('2024-04-27'),
('2024-04-28'),
('2024-04-29'),
('2024-04-30'),
('2024-05-01'),
('2024-05-02'),
('2024-05-03'),
('2024-05-04'),
('2024-05-05'),
('2024-05-06'),
('2024-05-07'),
('2024-05-08'),
('2024-05-09'),
('2024-05-10'),
('2024-05-11'),
('2024-05-12'),
('2024-05-13'),
('2024-05-14'),
('2024-05-15'),
('2024-05-16'),
('2024-05-17'),
('2024-05-18'),
('2024-05-19'),
('2024-05-20'),
('2024-05-21'),
('2024-05-22'),
('2024-05-23'),
('2024-05-24'),
('2024-05-25'),
('2024-05-26'),
('2024-05-27'),
('2024-05-28'),
('2024-05-29'),
('2024-05-30'),
('2024-05-31'),
('2024-06-01'),
('2024-06-02'),
('2024-06-03'),
('2024-06-04'),
('2024-06-05'),
('2024-06-06'),
('2024-06-07'),
('2024-06-08'),
('2024-06-09'),
('2024-06-10'),
('2024-06-11'),
('2024-06-12'),
('2024-06-13'),
('2024-06-14'),
('2024-06-15'),
('2024-06-16'),
('2024-06-17'),
('2024-06-18'),
('2024-06-19'),
('2024-06-20'),
('2024-06-21'),
('2024-06-22'),
('2024-06-23'),
('2024-06-24'),
('2024-06-25'),
('2024-06-26'),
('2024-06-27'),
('2024-06-28'),
('2024-06-29'),
('2024-06-30'),
('2024-07-01'),
('2024-07-02'),
('2024-07-03'),
('2024-07-04'),
('2024-07-05'),
('2024-07-06'),
('2024-07-07'),
('2024-07-08'),
('2024-07-09'),
('2024-07-10'),
('2024-07-11'),
('2024-07-12'),
('2024-07-13'),
('2024-07-14'),
('2024-07-15'),
('2024-07-16'),
('2024-07-17'),
('2024-07-18'),
('2024-07-19'),
('2024-07-20'),
('2024-07-21');

-- --------------------------------------------------------

--
-- Structure de la table `plat`
--

DROP TABLE IF EXISTS `plat`;
CREATE TABLE IF NOT EXISTS `plat` (
  `IDPLAT` int NOT NULL AUTO_INCREMENT,
  `IDCATEG` int NOT NULL,
  `NOMPLAT` char(70) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `DESCRIPTIF` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci,
  `IMAGEPATH` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`IDPLAT`),
  KEY `FK_PLAT_CATEGORIEPLAT` (`IDCATEG`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `plat`
--

INSERT INTO `plat` (`IDPLAT`, `IDCATEG`, `NOMPLAT`, `DESCRIPTIF`, `IMAGEPATH`) VALUES
(1, 1, 'Entrecote', 'vive la viande', ''),
(2, 3, 'Flan', 'tres moelleux', ''),
(3, 2, 'Salade', 'tres vert', ''),
(9, 1, 'Frites', 'Bien croustillante', ''),
(22, 1, 'Poisson', 'Cuit a l\'etouffee', ''),
(32, 1, 'Bourgignon', 'Bien consistant', ''),
(33, 3, 'Creme Brulee', 'Bien caramelise', ''),
(35, 3, 'Muffin au Nutella', 'Tres chocolate et moelleux', ''),
(37, 2, 'Soupe de poisson', 'Tres marin', ''),
(40, 1, 'Lasagne', 'Delicieux plat italien', ''),
(45, 1, 'Puree de pomme de terre', 'Ecrase bien onctueux de patates fraiches', ''),
(46, 3, 'Tiramisu', 'Un melange entre du chocolat onctueux et du cafe reposant dans un gateau moelleux', ''),
(47, 3, 'Feuillete aux pommes', 'Rafraichissant et legee  COUP DE COEUR CLIENT', ''),
(48, 3, 'Pain perdu', 'Recette revisite du pain perdu francais ', ''),
(49, 1, 'Wok de porc, crevettes et langoustines', 'Un wok terre-mer façon cuisine vietnamienne.', ''),
(50, 1, 'Jarret de porc laqué', 'Une spécialité alsacienne appelée aussi wädele. Laqué avec une marinade à base de miel de sauce barbecue, de sauce soja et d\'épices, il est savoureux et croustillant, impossible de lui résister.', ''),
(51, 2, 'Bouchées à la reine', 'La bouchée à la reine est une charcuterie pâtissière traditionnelle de la gastronomie française constituée d\'un vol-au-vent individuel et de sa garniture. Dans une pâte feuilletée, remplie de nos jours d\'ingrédients variés coupés en petits dés ou lamelles (volaille, ris de veau, jambon, champignons) et liés par une sauce épaisse sauce béchamel.', '');

-- --------------------------------------------------------

--
-- Structure de la table `proposerplat`
--

DROP TABLE IF EXISTS `proposerplat`;
CREATE TABLE IF NOT EXISTS `proposerplat` (
  `IDSERVICE` int NOT NULL,
  `IDPLAT` int NOT NULL,
  `DATE_SERVICE` date NOT NULL,
  `QUANTITEPROPOSEE` int DEFAULT NULL,
  `PRIXVENTE` decimal(15,2) DEFAULT NULL,
  `QUANTITEVENDUE` int DEFAULT NULL,
  PRIMARY KEY (`IDSERVICE`,`IDPLAT`,`DATE_SERVICE`),
  KEY `FK_PROPOSERPLAT_PLAT` (`IDPLAT`),
  KEY `FK_PROPOSERPLAT_DATE_SERVICE` (`DATE_SERVICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `proposerplat`
--

INSERT INTO `proposerplat` (`IDSERVICE`, `IDPLAT`, `DATE_SERVICE`, `QUANTITEPROPOSEE`, `PRIXVENTE`, `QUANTITEVENDUE`) VALUES
(1, 1, '2024-01-26', 200, 20.00, 30),
(1, 1, '2024-02-14', 100, 5.00, 10),
(1, 1, '2024-04-23', 10, 10.00, 0),
(1, 1, '2024-05-11', 10, 10.00, 0),
(1, 2, '2024-02-14', 10, 5.00, 10),
(1, 3, '2024-01-26', 100, 5.00, 50),
(1, 3, '2024-02-14', 100, 50.00, 10),
(1, 3, '2024-04-22', 60, 10.00, 30),
(1, 9, '2024-01-26', 100, 4.00, 0),
(1, 9, '2024-02-14', 15, 4.00, 5),
(1, 22, '2024-01-26', 100, 20.00, 20),
(1, 33, '2024-04-21', 60, 6.00, 50),
(1, 35, '2024-02-14', 50, 12.00, 30),
(1, 40, '2024-02-14', 10, 10.00, 10),
(2, 1, '2024-02-14', 10, 5.00, 10),
(2, 3, '2024-01-26', 300, 10.00, 50),
(2, 3, '2024-02-14', 100, 10.00, 0),
(2, 9, '2024-01-26', 70, 4.00, 0),
(2, 9, '2024-02-14', 100, 4.00, 0),
(2, 32, '2024-04-22', 200, 3.00, 100),
(2, 40, '2024-04-21', 100, 15.00, 70),
(2, 45, '2024-01-26', 50, 5.00, 0),
(2, 49, '2024-01-26', 64, 12.00, 0),
(2, 50, '2024-01-26', 100, 15.00, 0),
(3, 2, '2024-02-14', 100, 7.00, 10),
(3, 22, '2024-01-26', 100, 15.00, 0),
(3, 33, '2024-01-26', 70, 5.00, 0),
(3, 33, '2024-02-14', 50, 5.00, 0),
(3, 35, '2024-02-14', 50, 5.00, 0),
(3, 37, '2024-01-26', 200, 20.00, 70),
(3, 37, '2024-04-21', 150, 17.00, 100),
(3, 47, '2024-01-26', 80, 7.00, 0);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `IDSERVICE` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IDSERVICE`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`IDSERVICE`) VALUES
(1),
(2),
(3);

-- --------------------------------------------------------

--
-- Structure de la table `statututilisateur`
--

DROP TABLE IF EXISTS `statututilisateur`;
CREATE TABLE IF NOT EXISTS `statututilisateur` (
  `IDSTATUT` int NOT NULL AUTO_INCREMENT,
  `NOMSTATUT` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDSTATUT`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `statututilisateur`
--

INSERT INTO `statututilisateur` (`IDSTATUT`, `NOMSTATUT`) VALUES
(1, 'serveur'),
(2, 'chef_cuisinier'),
(3, 'chef_salle');

-- --------------------------------------------------------

--
-- Structure de la table `table_resto`
--

DROP TABLE IF EXISTS `table_resto`;
CREATE TABLE IF NOT EXISTS `table_resto` (
  `IDSERVICE` int NOT NULL,
  `DATE_SERVICE` date NOT NULL,
  `NUMTABLE` int NOT NULL,
  `IDUTILISATEUR` int NOT NULL,
  `NOMBREPLACE` int DEFAULT NULL,
  PRIMARY KEY (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`),
  KEY `FK_TABLE_RESTO_DATE_SERVICE` (`DATE_SERVICE`),
  KEY `FK_TABLE_RESTO_UTILISATEUR` (`IDUTILISATEUR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `table_resto`
--

INSERT INTO `table_resto` (`IDSERVICE`, `DATE_SERVICE`, `NUMTABLE`, `IDUTILISATEUR`, `NOMBREPLACE`) VALUES
(1, '2024-01-26', 1, 1, 4);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `IDUTILISATEUR` int NOT NULL AUTO_INCREMENT,
  `IDSTATUT` int NOT NULL,
  `LOGIN` char(32) DEFAULT NULL,
  `MDP` char(32) DEFAULT NULL,
  `NOM` char(32) DEFAULT NULL,
  `PRENOM` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDUTILISATEUR`),
  KEY `FK_UTILISATEUR_STATUTUTILISATEUR` (`IDSTATUT`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`IDUTILISATEUR`, `IDSTATUT`, `LOGIN`, `MDP`, `NOM`, `PRENOM`) VALUES
(1, 2, 'corentin', 'corentin', 'lartigue', 'corentin'),
(2, 1, 'yoann', 'yoann', 'lesgourgues', 'yoann');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK_COMMANDE_TABLE_RESTO` FOREIGN KEY (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`) REFERENCES `table_resto` (`IDSERVICE`, `DATE_SERVICE`, `NUMTABLE`);

--
-- Contraintes pour la table `commander`
--
ALTER TABLE `commander`
  ADD CONSTRAINT `FK_COMMANDER_COMMANDE` FOREIGN KEY (`IDCOMMANDE`) REFERENCES `commande` (`IDCOMMANDE`),
  ADD CONSTRAINT `FK_COMMANDER_PLAT` FOREIGN KEY (`IDPLAT`) REFERENCES `plat` (`IDPLAT`);

--
-- Contraintes pour la table `plat`
--
ALTER TABLE `plat`
  ADD CONSTRAINT `FK_PLAT_CATEGORIEPLAT` FOREIGN KEY (`IDCATEG`) REFERENCES `categorieplat` (`IDCATEG`);

--
-- Contraintes pour la table `proposerplat`
--
ALTER TABLE `proposerplat`
  ADD CONSTRAINT `FK_PROPOSERPLAT_DATE_SERVICE` FOREIGN KEY (`DATE_SERVICE`) REFERENCES `date_service` (`DATE_SERVICE`),
  ADD CONSTRAINT `FK_PROPOSERPLAT_PLAT` FOREIGN KEY (`IDPLAT`) REFERENCES `plat` (`IDPLAT`),
  ADD CONSTRAINT `FK_PROPOSERPLAT_SERVICE` FOREIGN KEY (`IDSERVICE`) REFERENCES `service` (`IDSERVICE`);

--
-- Contraintes pour la table `table_resto`
--
ALTER TABLE `table_resto`
  ADD CONSTRAINT `FK_TABLE_RESTO_DATE_SERVICE` FOREIGN KEY (`DATE_SERVICE`) REFERENCES `date_service` (`DATE_SERVICE`),
  ADD CONSTRAINT `FK_TABLE_RESTO_SERVICE` FOREIGN KEY (`IDSERVICE`) REFERENCES `service` (`IDSERVICE`),
  ADD CONSTRAINT `FK_TABLE_RESTO_UTILISATEUR` FOREIGN KEY (`IDUTILISATEUR`) REFERENCES `utilisateur` (`IDUTILISATEUR`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK_UTILISATEUR_STATUTUTILISATEUR` FOREIGN KEY (`IDSTATUT`) REFERENCES `statututilisateur` (`IDSTATUT`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
