package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.UserDao;
import com.example.walibixgr2eq6.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.SecureRandom;

/**
 * controleur de l'interface invité
 * permet à un invité (sans se connecter) de pouvoir réserver une attraction
 * crée un compte dans la bdd avec un mdp généré aléatoirement
 */
public class InviteController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private Label inviteMessageLabel;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    /**
     * déclenchée quand un utilisateur clique sur le bouton d'inscription en tant qu'invité
     * crée un compte dans la bdd avec un mdp généré aléatoirement
     * une fois les info entrées, envoie direct sur le choix de date pour la réservation
     * @param event
     */
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ChoixDate.fxml"));
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

    /**
     * vérifie si l'adresse mail entrée respect bien le format d'un mail
     * @param email
     * @return true si le format est bon, sinon erreur
     */
    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    /**
     * génère le mdp aléatoire pour l'utilisateur invité
     * @return
     */
    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    /**
     * gère le clic sur le logo
     * si l'utilisateur clique sur le logo, cela redirige vers l'interface de départ
     * @param event
     */
    @FXML
    private void onLogoClicked(MouseEvent event) { //recup code julien pour faire le retour à l'accueil en appuyant sur le logo
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/start-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}