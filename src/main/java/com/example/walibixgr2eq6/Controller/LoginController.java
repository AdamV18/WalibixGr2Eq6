package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.*;
import com.example.walibixgr2eq6.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private TextField passwordVisibleField;
    @FXML private CheckBox showPasswordCheckBox;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @FXML
    protected void onLoginButtonClick() {
        String email = emailField.getText();
        String motDePasse = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();

        UserDao userDao = new UserDao(daoFactory);
        User user = userDao.checkLogin(email, motDePasse);

        if (user != null) {
            try {
                Stage stage = (Stage) emailField.getScene().getWindow();
                String fxmlFile = user.isAdmin() ? "admin-view.fxml" : "client-view.fxml";
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/" + fxmlFile));
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    @FXML
    protected void togglePasswordVisibility() {
        boolean visible = passwordVisibleField.isVisible();
        if (visible) {
            passwordField.setText(passwordVisibleField.getText());
            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        } else {
            passwordVisibleField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);
        }
    }

    @FXML
    protected void onSignupRedirect() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/signup-view.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}