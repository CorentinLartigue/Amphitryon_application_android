<?php
require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/ProposerPlatDAO.php';

print(json_encode(ProposerPlatDAO::afficherDateServices()));
