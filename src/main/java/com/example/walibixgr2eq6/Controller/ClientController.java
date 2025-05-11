package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * controleur de la vue client
 * gestion des actions de navigations vers réservation d'une nouvelle résa (choixDate),
 * affichage historique et déconnexion
 */
public class ClientController {

    private Stage stage;

    /**
     * définition
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * action délenchée quand il y a un clic sur le bouton "Nouvelle réservation"
     * redirection vers ChoixDate.fxml
     * @param event
     */
    @FXML
    private void onNouvelleReservationClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ChoixDate.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * action délenchée quand il y a un clic sur le bouton "Historique des réservations"
     * redirection vers historique-view.fxml
     * @param event
     */
    @FXML
    private void onHistoriqueClick(ActionEvent event) {
        if (!Session.isLoggedIn()) {
            System.out.println("Aucun utilisateur connecté.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/historique-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * action délenchée quand il y a un clic sur le bouton "Se déconnecter"
     * redirection vers satrt-view.fxml
     * @param event
     */
    @FXML
    private void onLogoutClick(ActionEvent event) {
        Session.clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/start-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}