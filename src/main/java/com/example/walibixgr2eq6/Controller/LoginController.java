package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.*;
import com.example.walibixgr2eq6.Model.User;
import com.example.walibixgr2eq6.Session;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

/**
 * controleur de l'interface login (d'un membre ou admin)
 * permet à un utilisateur de se connecter avec un mail et un mdp
 * cache le mdp
 */
public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private TextField passwordVisibleField;
    @FXML private CheckBox showPasswordCheckBox;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    /**
     * appelée quand l'utilisateur clique sur le bouton connexion
     * récupère les identifiants et vérifie avec la bdd
     * redirige l'utilisateur vers l'interface client
     */
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
                Session.setCurrentUserId(user.getUserId());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    /**
     * utilisée quand l'utilisateur coche (ou décoche) la case pour afficher le mdp
     * affiche ou masque le mdp
     */
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

    /**
     * gère le clic sur le logo
     * si l'utilisateur clique sur le logo, cela redirige vers l'interface de départ
     * @param event
     */
    @FXML
    private void onLogoClicked(MouseEvent event) { //recup code julien pour faire le retour à l'accueil en appuyant sur le logo
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}