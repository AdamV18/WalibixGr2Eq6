package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.UserDao;
import com.example.walibixgr2eq6.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.sql.Date;

public class SignupController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordVisibleField;
    @FXML private Label signupMessageLabel;
    @FXML private TextField dateNaissance;
    @FXML private Label ageLabel;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dateNaissance != null && ageLabel != null) {
            dateNaissance.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue != null && !newValue.isEmpty()) {
                        int age = calculateAge(newValue);
                        ageLabel.setText(String.valueOf(age));
                    } else {
                        ageLabel.setText("");
                    }
                } catch (Exception e) {
                    ageLabel.setText("Date invalide");
                }
            });
        }
    }

    @FXML
    protected void onSignupButtonClick(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String motDePasse = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();
            String dateTexte = dateNaissance.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || dateTexte.isEmpty()) {
                signupMessageLabel.setText("Champs manquants, Veuillez remplir tous les champs.");
                return;
            }

            if (!isValidEmail(email)) {
                signupMessageLabel.setText("Email invalide, Veuillez entrer une adresse email valide.");
                return;
            }

            // Vérifier si la date est valide
            Date date = parseDateFromTextField(dateTexte);
            if (date == null) {
                signupMessageLabel.setText("Format de date invalide. Utilisez le format jj/mm/aaaa.");
                return;
            }

            UserDao userDao = new UserDao(daoFactory);
            if (userDao.userExists(email)) {
                signupMessageLabel.setText("Un utilisateur avec cet email existe déjà.");
                return;
            }

            int age = calculateAge(dateTexte);
            int categorie = 0;

            if (age < 18) {
                categorie = 1;
            } else if (age >= 65) {
                categorie = 3;
            } else {
                categorie = 2;
            }

            try (Connection conn = daoFactory.getConnection()) {
                String sql = "INSERT INTO user (email, mot_de_passe, admin, nom, prenom, date_naissance, type_client, offre_reduc_id) VALUES (?, ?, false, ?, ?, ?, 'Membre', ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, email);
                stmt.setString(2, motDePasse);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);
                stmt.setDate(5, date);
                stmt.setInt(6, categorie);
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
                signupMessageLabel.setText("Erreur lors de la création: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            signupMessageLabel.setText("Erreur inattendue: " + e.getMessage());
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

    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private int calculateAge(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(dateStr, formatter);
            LocalDate now = LocalDate.now();
            return Period.between(birthDate, now).getYears();
        } catch (DateTimeParseException e) {
            System.out.println("Date invalide: " + dateStr);
            return 0;
        }
    }

    public Date parseDateFromTextField(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            System.out.println("Date vide");
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            System.out.println("Date invalide: " + dateString);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors du parsing de la date: " + e.getMessage());
            return null;
        }
    }
}