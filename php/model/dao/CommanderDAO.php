<?php
class CommanderDAO{
    
	public static function modifierEtatCommande($idPlat, $idCommande, $etatPlat){
		$sql = "UPDATE commander SET ETATPLAT = :etatPlat WHERE IDPLAT = :idPlat AND IDCOMMANDE = :idCommande; " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
        $requetePrepa->bindParam(":idPlat", $idPlat);
        $requetePrepa->bindParam(":etatPlat", $etatPlat);
		return $requetePrepa->execute();
	}
}

?>