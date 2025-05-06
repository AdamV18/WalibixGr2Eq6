package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.UserDao;
import com.example.walibixgr2eq6.Model.User;
import com.example.walibixgr2eq6.Session;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignupController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordVisibleField;
    @FXML private Label signupMessageLabel;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @FXML
    protected void onSignupButtonClick(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();

        UserDao userDao = new UserDao(daoFactory);
        if (userDao.userExists(email)) {
            signupMessageLabel.setText("Un utilisateur avec cet email existe déjà.");
            return;
        }


        try (Connection conn = daoFactory.getConnection()) {
            String sql = "INSERT INTO user (email, mot_de_passe, admin, nom, prenom) VALUES (?, ?, false, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            stmt.setString(3, nom);
            stmt.setString(4, prenom);
            stmt.executeUpdate();
            signupMessageLabel.setText("Compte créé avec succès !");
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int userId = 0;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
            Session.setCurrentUserId(userId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            signupMessageLabel.setText("Erreur lors de la création.");
            e.printStackTrace();
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
}
