<?php
class PlatDAO{
    public static function creerPlat($idCateg,$nom,$descriptif){
        $requetePrepa = DBConnex::getInstance()->prepare("INSERT INTO plat (IDCATEG,NOMPLAT,DESCRIPTIF) VALUES (:idCateg, :nom, :descriptif)");
        $requetePrepa -> bindParam(':idCateg', $idCateg);
        $requetePrepa -> bindParam(':nom',$nom);
        $requetePrepa -> bindParam(':descriptif',$descriptif);
        $requetePrepa -> execute();
    }

    public static function modifierPlat($idPlat,$idCateg,$nom,$descriptif){
        $requetePrepa = DBConnex::getInstance()->prepare("UPDATE plat SET IDCATEG=:idCateg,NOMPLAT=:nom,DESCRIPTIF=:descriptif WHERE IDPLAT=:idPlat");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idCateg', $idCateg);
        $requetePrepa -> bindParam(':nom',$nom);
        $requetePrepa -> bindParam(':descriptif',$descriptif);
        $requetePrepa -> execute();
    }

    public static function supprimerPlat($idPlat){
        $requetePrepa = DBConnex::getInstance()->prepare("DELETE FROM plat WHERE  IDPLAT=:idPlat");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> execute();
     
    }

    public static function afficherLesPlats(){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDPLAT,NOMCATEG,NOMPLAT,DESCRIPTIF,IMAGEPATH FROM plat,categorieplat WHERE categorieplat.IDCATEG=plat.IDCATEG");
            $requetePrepa -> execute();
            $liste = $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
            
        }catch(Exception $e){
            $liste = "";
        }
        return $liste;   
    }



    public static function afficherLePlat($idPlat){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDPLAT,plat.IDCATEG,NOMPLAT,DESCRIPTIF FROM plat,categorieplat WHERE categorieplat.IDCATEG=plat.IDCATEG AND plat.IDPLAT=:idPlat");
            $requetePrepa -> bindParam(':idPlat', $idPlat);
            $requetePrepa -> execute();
            $liste = $requetePrepa->fetch();
        }catch(Exception $e){
            $liste = "";
        }
        return $liste; 
    }


}