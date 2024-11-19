<?php
class CategorieDAO{
    public static function afficherCategorie(){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDCATEG,NOMCATEG FROM categorieplat");
            $requetePrepa -> execute();
            $liste = $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
                
        }catch(Exception $e){
            $liste = "";
        }
        return $liste;   
    }

}