<?php
class CommandeDAO{
    
	public static function creerCommande($dateService, $numTable, $idService){
		try{
			$sql = "INSERT INTO COMMANDE (DATE_SERVICE, NUMTABLE, IDSERVICE, HEURECOMMANDE, ETATCOMMANDE) VALUES (:dateService, :numTable, :idService, CURTIME(), non réglée); " ;
			$requetePrepa = DBConnex::getInstance()->prepare($sql);
			$requetePrepa->bindParam(":dateService", $dateService);
            $requetePrepa->bindParam(":numTable" , $numTable);
            $requetePrepa->bindParam(":idService",$idService);
			$requetePrepa->execute();
			$reponse = $requetePrepa->fetch();
		}catch(Exception $e){
			$reponse = "ca marche pas";
		}
		return $reponse;
	}

    public static function modifierCommande($idCommande, $dateService, $numTable, $idService){
		$sql = "UPDATE COMMANDE SET DATE_SERVICE = :dateService, NUMTABLE = :numTable, IDSERVICE = :idService WHERE IDCOMMANDE = :idCommande; " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->bindParam(":dateService", $dateService);
        $requetePrepa->bindParam(":numTable" , $numTable);
        $requetePrepa->bindParam(":idService",$idService);
		return $requetePrepa->execute();
	}

    public static function supprimerCommande($idCommande){
		$sql = "DELETE FROM COMMANDE WHERE IDCOMMANDE = :idCommande;" ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		return $requetePrepa->execute();
	}

    public static function afficherUneCommande($idCommande){
		$sql = "SELECT * FROM COMMANDE WHERE IDCOMMANDE = :idCommande;" ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->execute();
        return $requetePrepa->fetchAll(PDO::FETCH_ASSOC); 
	}

    public static function reglerCommande($idCommande){
		$sql = "UPDATE COMMANDE SET ETATCOMMANDE = réglée WHERE IDCOMMANDE = :idCommande; " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		return $requetePrepa->execute();
	}
}

?>