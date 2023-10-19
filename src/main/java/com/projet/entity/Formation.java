package com.projet.entity;

public class Formation {
    private Integer idFormation;
    private String nomFormation;
    private String promotion;

    public Formation() {
    }

    public Formation(Integer idFormation, String nomFormation, String promotion) {
        this.idFormation = idFormation;
        this.nomFormation = nomFormation;
        this.promotion = promotion;
    }

    public Integer getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Integer idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}
