package com.example.walibixgr2eq6.Model;

import java.time.LocalDate;

/**
 * représente un utilisateur de Walibix
 * peut être un client, admin ou invité
 */
public class User {
    private int userId;
    private String email;
    private String motDePasse;
    private boolean admin;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String typeClient;
    private Integer offreReducId;

    /**
     * constructeur complet de la classe user
     * @param userId
     * @param email
     * @param motDePasse
     * @param admin
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param typeClient
     * @param offreReducId
     */
    public User(int userId, String email, String motDePasse, boolean admin, String nom, String prenom, LocalDate dateNaissance, String typeClient, Integer offreReducId) {
        this.userId = userId;
        this.email = email;
        this.motDePasse = motDePasse;
        this.admin = admin;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.typeClient = typeClient;
        this.offreReducId = offreReducId;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getTypeClient() { return typeClient; }
    public void setTypeClient(String typeClient) { this.typeClient = typeClient; }

    public Integer getOffreReducId() { return offreReducId; }
    public void setOffreReducId(Integer offreReducId) { this.offreReducId = offreReducId; }

    /**
     * retourne une représentation (texte) de l'utilisateur
     * @return
     */
    @Override
    public String toString() {
        return nom + " " + prenom + " (" + email + ")";
    }
}
