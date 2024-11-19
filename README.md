# Amphitryon Android

## Introduction
**Amphitryon** est une application Android innovante qui permet de gérer des services de restauration en ligne, en particulier la création et la gestion des plats ainsi que leur rattachement à différents services (matin, midi, soir). Cette application repose sur un backend PHP connecté à une base de données MySQL, ce qui permet une gestion fluide et dynamique des données.

Le projet est divisé en deux parties principales :
- **Backend PHP** : Gère les requêtes et les interactions avec la base de données.
- **Application Android** : Permet aux utilisateurs d'interagir avec le backend pour effectuer des actions telles que la gestion des plats et services.

## Technologies Utilisées
### Backend
- **PHP** : Pour créer les scripts nécessaires à la gestion des données.
- **MySQL** : Base de données relationnelle pour stocker les informations des plats, services, et utilisateurs.
- **WampServer** : Environnement de développement local pour PHP, Apache, et MySQL.
  
### Frontend (Application Android)
- **Android Studio** : IDE pour le développement de l'application Android.
- **Java/Kotlin** : Langages utilisés pour développer l'application Android.

### Installation

## Prérequis

Clonez ce dépôt sur votre machine locale :
   ``` `bash
git clone https://github.com/CorentinLartigue/M2L_site_statique.git 
 ``` `
### Backend PHP
1. **Installer WampServer** : Téléchargez et installez WampServer pour gérer Apache, MySQL et PHP localement.
2. **Créer un VirtualHost** : Lancez WampServer et créez un VirtualHost dans la gestion des VirtualHosts pour l'URL où vous stockez votre dossier PHP.
3. **Importer la base de données** :
   - Accédez à PhpMyAdmin via WampServer.
   - Importez la base de données en utilisant le script SQL fourni.
4. **Configurer les paramètres PHP** :
   - Ouvrez le fichier `param.php` dans le dossier `php/`.
   - Modifiez les paramètres de connexion (`user`, `pass`, `dsn`, `dbName`) pour correspondre à ceux définis dans PhpMyAdmin.

### Application Android
1. **Ouvrir le projet dans Android Studio** :
   - Ouvrez uniquement le dossier `android/` dans Android Studio.
2. **Modifier les URL de requêtes PHP** :
   - Dans l'application Android, vous devez modifier les fichiers contenant des requêtes PHP (indiqués par des commentaires ou entourés en rouge).
   - Remplacez les occurrences de `localhost` par l'adresse IP de votre appareil.
3. **Modifier le fichier `network_security_config.xml`** :
   - Dans ce fichier, ajoutez l'adresse IP de votre appareil pour autoriser les requêtes réseau locales.

## Fonctionnalités Développées

### Gestion des Plats et Services
Ma contribution principale a consisté à développer les fonctionnalités de gestion des plats et services pour les utilisateurs ayant le rôle de **Chef Cuisinier**.

- **Types de plats** : Il existe trois types de plats : **Entrée**, **Plat principal**, et **Dessert**.
- **Services** : Les plats peuvent être associés à différents services de restauration, définis comme **Matin**, **Midi**, et **Soir**.
  
#### Fonctionnalités disponibles pour un Chef Cuisinier :
1. **Gestion des Plats** :
   - **Afficher** la liste des plats disponibles.
   - **Ajouter** un nouveau plat avec sa description, son type (entrée, plat principal, dessert), son prix et ses quantités.
   - **Modifier** un plat existant (prix, quantité disponible, etc.).
   - **Supprimer** un plat de la base de données.

2. **Gestion des Services** :
   - **Afficher** les plats proposés pour chaque service.
   - **Ajouter** un plat à un service spécifique (matin, midi ou soir).
   - **Modifier** les détails des plats associés à un service, tels que le prix de vente, les quantités vendues et proposées.
   - **Supprimer** un plat d'un service.

Cette fonctionnalité permet une gestion souple et efficace des offres de restauration selon l'heure de la journée, et elle permet aux chefs cuisiniers de maintenir une carte dynamique.

## Lancer l'application
1. **Backend** :
   - Lancez WampServer et accédez à votre VirtualHost via l'URL dans votre navigateur.
   - Testez les requêtes PHP en accédant au contrôleur approprié dans votre navigateur.
2. **Android** :
   - Lancez l'application Android sur un appareil ou un émulateur.
   - Assurez-vous que l'adresse IP de votre appareil est correctement configurée dans `network_security_config.xml`.
   - Testez la communication entre l'application et le serveur PHP.

Ce projet est le fruit d'une collaboration et permet de comprendre la manière dont les technologies backend et mobile peuvent interagir pour offrir des services de gestion de restauration performants et intuitifs.
