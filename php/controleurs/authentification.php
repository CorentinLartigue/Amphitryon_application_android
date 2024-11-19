<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/UtilisateurDAO.php';

print(json_encode(UtilisateurDAO::authentification($_POST['LOGIN'], $_POST['MDP'])));


?>