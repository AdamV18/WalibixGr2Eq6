package com.example.walibixgr2eq6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

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
        switchScene("client-view.fxml");
    }

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