<?php
class ProposerPlatDAO{

    public static function afficherDateServices(){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT DATE_SERVICE FROM date_service ");
        $requetePrepa -> execute();
            $liste = $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
        }catch(Exception $e){
            $liste = "";
        }
        return $liste;   
    }

    public static function afficherNumServices(){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT IDSERVICE FROM service ");
        $requetePrepa -> execute();
            $liste = $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
        }catch(Exception $e){
            $liste = "";
        }
        return $liste;   
    }

    public static function supprimerPlatProposer($idPlat,$idService,$date_service){
        $requetePrepa = DBConnex::getInstance()->prepare("DELETE FROM proposerplat WHERE  IDPLAT=:idPlat AND IDSERVICE=:idService AND DATE_SERVICE=:date_service");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> execute();
     
    }

    public static function creerPlatProposer($idPlat,$idService,$date_service,$quantitePropose,$quantiteVendu,$prixVente){
        $requetePrepa = DBConnex::getInstance()->prepare("INSERT INTO proposerplat (IDPLAT,IDSERVICE,DATE_SERVICE,QUANTITEPROPOSEE,QUANTITEVENDUE,PRIXVENTE) VALUES (:idPlat, :idService, :date_service, :quantitePropose, :quantiteVendu, :prixVente)");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> bindParam(':quantitePropose', $quantitePropose);
        $requetePrepa -> bindParam(':quantiteVendu', $quantiteVendu);
        $requetePrepa -> bindParam(':prixVente', $prixVente);
        $requetePrepa -> execute();
    }

    public static function modifierPlatProposee($idPlat,$idService,$date_service,$quantitePropose,$quantiteVendu,$prixVente){
        $requetePrepa = DBConnex::getInstance()->prepare("UPDATE proposerplat SET QUANTITEPROPOSEE=:quantitePropose, QUANTITEVENDUE=:quantiteVendu,PRIXVENTE=:prixVente WHERE IDPLAT=:idPlat AND IDSERVICE=:idService AND DATE_SERVICE=:date_service");
        $requetePrepa -> bindParam(':idPlat', $idPlat);
        $requetePrepa -> bindParam(':idService', $idService);
        $requetePrepa -> bindParam(':date_service', $date_service);
        $requetePrepa -> bindParam(':quantitePropose', $quantitePropose);
        $requetePrepa -> bindParam(':quantiteVendu', $quantiteVendu);
        $requetePrepa -> bindParam(':prixVente', $prixVente);
        $requetePrepa -> execute();
    }

    public static function afficherLesPlatsPourUnService($idService,$date_service){
        try{
            $requetePrepa =DBConnex::getInstance()->prepare("SELECT plat.IDPLAT,NOMCATEG,NOMPLAT,DESCRIPTIF,QUANTITEPROPOSEE, QUANTITEVENDUE,PRIXVENTE FROM plat,categorieplat,proposerplat WHERE categorieplat.IDCATEG=plat.IDCATEG AND plat.IDPLAT=proposerplat.IDPLAT AND proposerplat.IDSERVICE=:idService AND proposerplat.DATE_SERVICE=:date_service");
            $requetePrepa -> bindParam(':idService', $idService);
            $requetePrepa -> bindParam(':date_service', $date_service);
            $requetePrepa -> execute();
            $liste = $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
            
        }catch(Exception $e){
            $liste = "";
        }
        return $liste;   
    }

}