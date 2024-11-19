<?php
require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CategorieDAO.php';

print(json_encode(CategorieDAO::afficherCategorie ()));