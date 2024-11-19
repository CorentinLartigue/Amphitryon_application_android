package com.example.lamphitryon;

public class PlatWithQuantities {
    private Plat plat;
    private String quantiteProposee;
    private String quantiteVendue;

    private String prixVente;

    public PlatWithQuantities(Plat plat, String quantiteProposee, String quantiteVendue, String prixVente) {
        this.plat = plat;
        this.quantiteProposee = quantiteProposee;
        this.quantiteVendue = quantiteVendue;
        this.prixVente = prixVente;
    }
    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public String getQuantiteProposee() {
        return quantiteProposee;
    }

    public void setQuantiteProposee(String quantiteProposee) {
        this.quantiteProposee = quantiteProposee;
    }


    public String getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(String quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }
    public String getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }

}
