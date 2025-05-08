package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button statsButton;

    @FXML
    protected void onManageUser() {
        //showMessage("Redirection vers la gestion des utilisateurs.");
        switchScene("admin_edit_user.fxml");
    }

    @FXML
    protected void onManageAttractions() {
        //showMessage("Redirection vers la gestion des attractions.");
        switchScene("admin_edit_attraction.fxml");
    }

    @FXML
    protected void onManageReductions() {
        //showMessage("Redirection vers la gestion des réductions.");
        switchScene("admin_edit_offrereduc.fxml");
    }

    @FXML
    protected void onViewStatistics() {
        //showMessage("Redirection vers les statistiques.");
        switchScene("Statistics.fxml");
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void switchScene(String fxmlFile) {
        try {
            Stage stage = (Stage) statsButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/" + fxmlFile));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}