<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommanderDAO.php';

print(CommanderDAO::modifierEtatCommande($_POST['idPlat'], $_POST['idCommande'], $_POST['etatPlat']));
