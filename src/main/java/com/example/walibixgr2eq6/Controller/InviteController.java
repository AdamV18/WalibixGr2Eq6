package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.UserDao;
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
import java.security.SecureRandom;


public class InviteController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private Label inviteMessageLabel;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");


    @FXML
    protected void onInviteButtonClick(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String motDePasseTemporaire = generatePassword();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
                inviteMessageLabel.setText("Champs manquants, Veuillez remplir tous les champs.");
                return;
            }

            if (!isValidEmail(email)) {
                inviteMessageLabel.setText("Email invalide, Veuillez entrer une adresse email valide.");
                return;
            }

            UserDao userDao = new UserDao(daoFactory);
            if (userDao.userExists(email)) {
                inviteMessageLabel.setText("Un utilisateur avec cet email existe déjà.");
                return;
            }

            try (Connection conn = daoFactory.getConnection()) {
                String sql = "INSERT INTO user (email, mot_de_passe, admin, nom, prenom, type_client) VALUES (?, ?, false, ?, ?, 'Invite')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, email);
                stmt.setString(2, motDePasseTemporaire);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);

                stmt.executeUpdate();
                inviteMessageLabel.setText("Connecté en tant qu'invité !");
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int userId = 0;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
                Session.setCurrentUserId(userId);
                // Modifier plus tard vers reservation-view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
                Scene scene = new Scene(loader.load(), 900, 600);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                inviteMessageLabel.setText("Erreur lors de la création: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            inviteMessageLabel.setText("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}