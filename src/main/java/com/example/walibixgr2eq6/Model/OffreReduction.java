package com.example.walibixgr2eq6.Model;

public class OffreReduction {
    private int offreReducId;
    private String nom;
    private String description;
    private double pourcentage;
    private int conditionAgeMin;
    private int conditionAgeMax;

    public OffreReduction(int offreReducId, String nom, String description, double pourcentage, int conditionAgeMin, int conditionAgeMax) {
        this.offreReducId = offreReducId;
        this.nom = nom;
        this.description = description;
        this.pourcentage = pourcentage;
        this.conditionAgeMin = conditionAgeMin;
        this.conditionAgeMax = conditionAgeMax;
    }

    public int getOffreReducId() { return offreReducId; }
    public void setOffreReducId(int offreReducId) { this.offreReducId = offreReducId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }

    public int getConditionAgeMin() { return conditionAgeMin; }
    public void setConditionAgeMin(int conditionAgeMin) { this.conditionAgeMin = conditionAgeMin; }

    public int getConditionAgeMax() { return conditionAgeMax; }
    public void setConditionAgeMax(int conditionAgeMax) { this.conditionAgeMax = conditionAgeMax; }


    @Override
    public String toString() {
        return nom + " (" + pourcentage + "%)";
    }
}

