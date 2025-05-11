package com.example.walibixgr2eq6.Model;

/**
 * represente une offre de réduction avec son id, son nom, sa description,
 * son pourcentage, sa condition d'age min et max
 */
public class OffreReduction {
    private int offreReducId;
    private String nom;
    private String description;
    private double pourcentage;
    private int conditionAgeMin;
    private int conditionAgeMax;

    /**
     * constructeur de l'offre de réduction
     * @param offreReducId
     * @param nom
     * @param description
     * @param pourcentage
     * @param conditionAgeMin
     * @param conditionAgeMax
     */
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

    /**
     * retourne une representation textuelle de l'offre de réduction  avec le nom et le pourcentage définit
     * @return
     */
    @Override
    public String toString() {
        return nom + " (" + pourcentage + "%)";
    }
}

