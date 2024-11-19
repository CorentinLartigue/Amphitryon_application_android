<?php
require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/PlatDAO.php';

print(json_encode(PlatDAO::modifierPlat($_POST['IDPLAT'],$_POST['IDCATEG'],$_POST['NOMPLAT'],$_POST['DESCRIPTIF'])));
