<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommandeDAO.php';

print(CommanderDAO::creerCommande($_POST['dateService'], $_POST['numTable'], $_POST['idService']));
print(CommanderDAO::modifierCommande($_POST['idCommande'], $_POST['dateService'], $_POST['numTable'], $_POST['idService']));
print(CommanderDAO::supprimerCommande($_POST['idCommande']);
print(CommanderDAO::reglerCommande($_POST['idCommande']);

print(json_encode(CommanderDAO::afficherUneCommande($_POST['idCommande'])));
