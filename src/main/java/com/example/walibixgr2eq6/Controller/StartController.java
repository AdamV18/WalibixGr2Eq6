package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * controleur de l'interface de départ
 * permet à l'utilisateur de choisir entre : se connecter, s'inscrire ou continuer en tant qu'invité
 */
public class StartController {

    @FXML
    private Button loginButton;

    @FXML
    protected void onLoginClick() {
        switchScene("login-view.fxml");
    }

    @FXML
    protected void onSignupClick() {
        switchScene("signup-view.fxml");
    }

    @FXML
    protected void onGuestClick() {
        switchScene("invite-view.fxml");
    }

    /**
     * permet de changer de scène en fonction du fxml donné
     * @param fxmlFile
     */
    private void switchScene(String fxmlFile) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/" + fxmlFile));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}