package com.example.walibixgr2eq6.Model;

/**
 * represente une attraction avec son id, son nom
 * son type, sa description, son prix de base et le chemin vers son image
 */
public class Attraction {
    private int attractionId;
    private String nom;
    private String typeAttrac;
    private String description;
    private double prixBase;
    private String image;

    /**
     * constructeur de l'attraction
     * @param attractionId
     * @param nom
     * @param typeAttrac
     * @param description
     * @param prixBase
     * @param image
     */
    public Attraction(int attractionId, String nom, String typeAttrac, String description, double prixBase, String image) {
        this.attractionId = attractionId;
        this.nom = nom;
        this.typeAttrac = typeAttrac;
        this.description = description;
        this.prixBase = prixBase;
        this.image = image;
    }

    public int getAttractionId() { return attractionId; }
    public void setAttractionId(int attractionId) { this.attractionId = attractionId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getTypeAttrac() { return typeAttrac; }
    public void setTypeAttrac(String typeAttrac) { this.typeAttrac = typeAttrac; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrixBase() { return prixBase; }
    public void setPrixBase(double prixBase) { this.prixBase = prixBase; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    /**
     * retourne une representation textuelle de l'attraction  avec le nom et le prix de base
     * @return
     */
    @Override
    public String toString() {
        return nom + " (" + prixBase + "€)";
    }
}

