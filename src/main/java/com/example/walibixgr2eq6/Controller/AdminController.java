package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controleur interface Administrateur
 * gestion des actions de navigations vers les autres pages admin
 * (user, attractions, réduc, stat) et déconnexion
 */
public class AdminController {

    @FXML
    private Button statsButton;

    /**
     * redirection vers vue user
     */
    @FXML
    protected void onManageUser() {
        //showMessage("Redirection vers la gestion des utilisateurs.");
        switchScene("admin_edit_user.fxml");
    }

    /**
     * redirection vers vue attractions
     */
    @FXML
    protected void onManageAttractions() {
        //showMessage("Redirection vers la gestion des attractions.");
        switchScene("admin_edit_attraction.fxml");
    }

    /**
     * redirection vers vue reduc
     */
    @FXML
    protected void onManageReductions() {
        //showMessage("Redirection vers la gestion des réductions.");
        switchScene("admin_edit_offrereduc.fxml");
    }

    /**
     * redirection vers vue stats
     */
    @FXML
    protected void onViewStatistics() {
        //showMessage("Redirection vers les statistiques.");
        switchScene("Statistics.fxml");
    }

    /**
     * deconnexion de l'user
     * vide la session lors de la déconnexon
     */
    @FXML
    private void onLogoutClick() {
        Session.clear();
        switchScene("start-view.fxml");
    }

    /**
     * affiche une boite de dialogue d'infos
     * @param msg
     */
    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * chargement et affichage d'une nouvelle scène depuis un fichier FXML
     * @param fxmlFile
     */
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