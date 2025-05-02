package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignupController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label signupMessageLabel;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @FXML
    protected void onSignupButtonClick() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = passwordField.getText();

        try (Connection conn = daoFactory.getConnection()) {
            String sql = "INSERT INTO user (email, mot_de_passe, admin, nom, prenom) VALUES (?, ?, false, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            stmt.setString(3, nom);
            stmt.setString(4, prenom);
            stmt.executeUpdate();
            signupMessageLabel.setText("Compte créé avec succès !");
        } catch (Exception e) {
            signupMessageLabel.setText("Erreur lors de la création.");
            e.printStackTrace();
        }
    }
}
