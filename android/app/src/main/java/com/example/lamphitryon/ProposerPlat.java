package com.example.lamphitryon;

import java.util.Date;

public class ProposerPlat {
    private int idService;
    private int idPlat;
    private Date dateService;
    private int quantiteProposee;
    private double prixVente;
    private int quantiteVendue;

    public ProposerPlat(int idService, int idPlat, Date dateService, int quantiteProposee, double prixVente, int quantiteVendue) {
        this.idService = idService;
        this.idPlat = idPlat;
        this.dateService = dateService;
        this.quantiteProposee = quantiteProposee;
        this.prixVente = prixVente;
        this.quantiteVendue = quantiteVendue;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public Date getDateService() {
        return dateService;
    }

    public void setDateService(Date dateService) {
        this.dateService = dateService;
    }

    public int getQuantiteProposee() {
        return quantiteProposee;
    }

    public void setQuantiteProposee(int quantiteProposee) {
        this.quantiteProposee = quantiteProposee;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }
}
