package com.example.lamphitryon;

public class Plat {
    private Integer numero;
    private String nom;
    private String descriptif;

    public Plat(Integer numero, String nom, String descriptif) {
        this.numero = numero;
        this.nom = nom;
        this.descriptif = descriptif;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getDescriptif() {
        return descriptif;
    }


}
