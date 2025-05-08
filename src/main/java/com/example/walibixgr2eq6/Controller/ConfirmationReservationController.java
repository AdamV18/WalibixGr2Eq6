package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmationReservationController {

    @FXML
    private Label attractionNomLabel; // Label pour afficher le nom de l'attraction
    @FXML
    private Label creneauChoisiLabel; // Label pour afficher le créneau choisi

    private String attractionNom;
    private String creneauChoisi;

    // Méthode pour définir le nom de l'attraction
    public void setAttractionNom(String attractionNom) {
        this.attractionNom = attractionNom;
        // Mettre à jour le label avec le nom de l'attraction
        attractionNomLabel.setText(attractionNom);
    }

    // Méthode pour définir le créneau choisi
    public void setCreneauChoisi(String creneauChoisi) {
        this.creneauChoisi = creneauChoisi;
        // Mettre à jour le label avec le créneau choisi
        creneauChoisiLabel.setText(creneauChoisi);
    }

    // Méthode pour confirmer la réservation et passer à la page suivante (par exemple, paiement ou autre étape)
    @FXML
    private void confirmerReservation() {
        // Ici, tu pourrais enregistrer la réservation dans la base de données, afficher une confirmation, etc.
        System.out.println("Réservation confirmée : " + attractionNom + " à " + creneauChoisi);
    }

    // Méthode pour revenir à la page précédente (par exemple, liste des attractions)
    @FXML
    private void revenirListeAttractions() {
        // Logic pour revenir à la liste des attractions
    }
}
