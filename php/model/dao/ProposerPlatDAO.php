<?php
class ProposerPlatDAO{

    public static function afficherPlatProposer($idService){
        $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDSERVICE,DATE_SERVICE,PRIXVENTE,QUANTITEPROPOSEE,NOMPLAT,DESCRIPTIF,NOMCATEG  FROM PROPOSERPLAT,PLAT,CATEGORIEPLAT WHERE IDSERVICE=:idService");
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> execute();
    }

    public static function supprimerPlatProposer($idPlat,$idService,$date_service){
        $requetePrepa = DBConnex::getInstance()->prepare("DELETE FROM PROPOSERPLAT WHERE  IDPLAT=:idPlat AND IDSERVICE=:idService AND DATE_SERVICE=:date_service");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> execute();
     
    }

    public static function creerPlatProposer($idPlat,$idService,$date_service,$quantitePropose,$prixVente){
        $requetePrepa = DBConnex::getInstance()->prepare("INSERT INTO PLAT (IDPLAT,IDSERVICE,DATE_SERVICE,QUANTITEPROPOSEE,PRIXVENTE) VALUES (:idPlat, :idService, :date_service, :quantitePropose, :prixVente)");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> bindParam(':quantitePropose', $quantitePropose);
        $requetePrepa -> bindParam(':prixVente', $prixVente);
        $requetePrepa -> execute();
    }

    public static function modifierPlatProposer($idPlat,$idService,$date_service,$quantitePropose,$prixVente){
        $requetePrepa = DBConnex::getInstance()->prepare("UPDATE PLAT SET QUANTITEPROPOSEE=:quantitePropose, PRIXVENTE=:prixVente WHERE IDPLAT=:idPlat AND IDSERVICE=:idService AND DATE_SERVICE=:date_service");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> bindParam(':quantitePropose', $quantitePropose);
        $requetePrepa -> bindParam(':prixVente', $prixVente);
        $requetePrepa -> execute();
    }

    public static function afficherPlatProposer($idService,$date_service){
        $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDSERVICE,DATE_SERVICE,PRIXVENTE,QUANTITEPROPOSEE,NOMPLAT,DESCRIPTIF,NOMCATEG  FROM PROPOSERPLAT,PLAT,CATEGORIEPLAT WHERE IDSERVICE=:idService AND DATE_SERVICE=:date_service AND CATEGORIEPLAT.IDCATEG=PLAT.IDCATEG");
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':DATE_SERVICE', $DATE_SERVICE);
        $requetePrepa -> execute();
    }


}