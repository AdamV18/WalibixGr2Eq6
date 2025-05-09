package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FinReservationController {

    @FXML
    private Button boutonRetourAccueil;

    @FXML
    public void initialize() {
    }

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


}
