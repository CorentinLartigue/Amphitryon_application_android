<?php
class platDAO{
    public static function creerPlat($idCateg,$nom,$descriptif){
        $requetePrepa = DBConnex::getInstance()->prepare("INSERT INTO PLAT (IDCATEG,NOMPLAT,DESCRIPTIF) VALUES (:idCateg, :nom, :descriptif)");
        $requetePrepa -> bindParam(':idCateg', $idCateg);
        $requetePrepa -> bindParam(':nom',$nom);
        $requetePrepa -> bindParam(':descriptif',$descriptif);
        $requetePrepa -> execute();
    }

    public static function modifierPlat($idPlat,$idCateg,$nom,$descriptif){
        $requetePrepa = DBConnex::getInstance()->prepare("UPDATE PLAT SET IDCATEG=:idCateg,NOMPLAT=:nom,DESCRIPTIF=:descriptif WHERE IDPLAT=:idPlat");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idCateg', $idCateg);
        $requetePrepa -> bindParam(':nom',$nom);
        $requetePrepa -> bindParam(':descriptif',$descriptif);
        $requetePrepa -> execute();
    }

    public static function supprimerPlat($idPlat){
        $requetePrepa = DBConnex::getInstance()->prepare("DELETE FROM PLAT WHERE  IDPLAT=:idPlat");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> execute();
     
    }

    public static function afficherPlat(){
        $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDPLAT,NOMCATEG,NOMPLAT,DESCRIPTIF FROM PLAT,CATEGORIEPLAT WHERE CATEGORIEPLAT.IDCATEG=PLAT.IDCATEG");
        $requetePrepa -> execute();
    }


}