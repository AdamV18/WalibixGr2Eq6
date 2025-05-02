package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminController {

    @FXML
    protected void onManageAttractions() {
        showMessage("Redirection vers la gestion des attractions.");
        // TODO : Charger la vue FXML de gestion des attractions
    }

    @FXML
    protected void onManageReductions() {
        showMessage("Redirection vers la gestion des réductions.");
        // TODO : Charger la vue FXML de gestion des réductions
    }

    @FXML
    protected void onViewClients() {
        showMessage("Redirection vers la liste des clients.");
        // TODO : Charger la vue FXML de visualisation des clients
    }

    @FXML
    protected void onViewStatistics() {
        showMessage("Redirection vers les statistiques.");
        // TODO : Charger la vue FXML des statistiques (camemberts, histogrammes...)
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}