<?php
class CategorieDAO{
    public static function afficherCategorie(){
        $requetePrepa =DBConnex::getInstance()->prepare("SELECT NOMCATEG FROM CATEGORIEPLAT");
        $requetePrepa -> execute();
    }
}