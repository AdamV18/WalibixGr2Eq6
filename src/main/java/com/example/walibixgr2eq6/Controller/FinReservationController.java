package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Model.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * controleur pour l'interface FinReservation
 * affiche le résumé d'une réservation terminée
 * affiche un bouton de retour qui renvoie à l'accueil si on clique dessus
 */
public class FinReservationController {

    @FXML
    private Button boutonRetourAccueil;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelAttraction;

    @FXML
    private Label labelPrix;

    @FXML
    private Label labelPrixReduc;

    @FXML
    private Label labelCreneau;

    private Reservation reservation;

    @FXML
    public void initialize() {
    }


    /**
     * gère le bouton retour
     * redirige l'utilisateur vers l'interface client si le bouton est cliqué
     */
    @FXML
    private void retourAccueil() {
        try { // retour sur la page de choix de l'attraction parce que pour l'instant on a pas accès à l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) boutonRetourAccueil.getScene().getWindow(); //affiche l'autre page
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Choix Attractions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * met à jour l'affichag du résumé de l'attraction avec les bonnes infos
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            labelDate.setText("Date : " + reservation.getDate());
            labelAttraction.setText("Attraction : " + reservation.getAttractionNom());
            labelCreneau.setText("Heure : " + reservation.getHeure());
            labelPrixReduc.setText("Prix payé : " + reservation.getPrixAvecReduc() + "€");

        } else {
            System.out.println("Erreur : Reservation nulle.");
        }
    }



}
